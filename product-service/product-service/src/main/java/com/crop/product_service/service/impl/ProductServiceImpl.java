package com.crop.product_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.crop.product_service.dto.InventoryCreateRequest;
import com.crop.product_service.dto.InventoryCreateResponse;
import com.crop.product_service.dto.ProductRequest;
import com.crop.product_service.dto.ProductResponse;
import com.crop.product_service.dto.ProductSearchRequest;
import com.crop.product_service.dto.UserDetailsResponse;
import com.crop.product_service.entity.Product;
import com.crop.product_service.kafka.Producer.ProductEventProducer;
import com.crop.product_service.kafka.event.ProductCreatedEvent;
import com.crop.product_service.repository.ProductRepository;
import com.crop.product_service.service.AuthClient;
import com.crop.product_service.service.InvenotryClient;
import com.crop.product_service.service.ProductService;
import com.crop.product_service.service.exception.ProductFoundException;
import com.crop.product_service.service.exception.ResourceNotFoundException;
import com.crop.product_service.specification.ProductSpecification;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	public final ProductRepository productRepository;

	private final AuthClient authClient;

	private final InvenotryClient invenotryClient;
	
	private final ProductEventProducer productEventProducer;

	@Override
	public ProductResponse createProduct(ProductRequest request) throws RuntimeException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Product product = new Product();

		product = Product.builder().name(request.getName()).category(request.getCategory())
				.description(request.getDescription()).active(product.getActive()).createdAt(product.getCreatedAt())
				.imageUrl(request.getImageUrl()).price(request.getPrice()).email(authentication.getName())
				.offerPercentage(request.getOfferPercentage()).updateAt(product.getUpdateAt()).build();
		if (!productRepository.existsByNameIgnoreCase(request.getName())) {
			Product saveProduct = productRepository.save(product);
			ProductCreatedEvent productCreatedEvent=ProductCreatedEvent.builder().productId(product.getId()).name(product.getName()).AvailableQuantity(request.getQuantity()).category(product.getCategory()).build();
			//productEventProducer.publishProductCreatedEvent(productCreatedEvent);
			try {
				productEventProducer.publishProductCreatedEvent(productCreatedEvent);
				return MapToResponse(saveProduct);
			} catch (RuntimeException e) {
				e.printStackTrace();
				if (saveProduct != null) {
					productRepository.deleteById(saveProduct.getId());
				}
			}

		} else {
			throw new ProductFoundException("Product already exists", HttpStatus.FOUND);
		}
		return null;

	}

	private ProductResponse MapToResponse(Product product) {
		return new ProductResponse(product.getId(), product.getEmail(),
				// product.getFarmerName(),
				product.getPrice(),
				// product.getContact(),
				product.getName(), product.getCategory(), product.getDescription(), product.getImageUrl(),
				product.getActive(), product.getOfferPercentage(), product.getRegisteredDate(), product.getCreatedAt(),
				product.getUpdateAt());
		// return null;
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		List<Product> allProducts = productRepository.findAll();
		if (allProducts != null) {
			return allProducts.stream().map(this::MapToResponse).toList();
		}
		return null;
	}

	@Override
	public ProductResponse findProductById(@Valid Long id) {

		Optional<Product> product = Optional.of(
				productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prouct not found")));
	//	UserDetailsResponse farmer = authClient.getUserDetailsByFarmerEmail(product.get().getEmail());
	//	System.out.println("Feign clin=ent response" + farmer.getEmail() + farmer.getName() + farmer.getPhone());

		return MapToResponse(product.get());
	}

	@Override
	public List<ProductResponse> getProductByCategory(@Valid String category) {
		List<Product> productByCategory = productRepository.findByCategoryIgnoreCase(category);
		if (productByCategory != null) {
			return productByCategory.stream().map(this::MapToResponse).toList();
		} else {
			throw new ResourceNotFoundException("Prouct not found");
		}
	}

	@Override
	public List<ProductResponse> searchProductByName(@Valid String name) {

		List<Product> productByName = productRepository.findByNameContainingIgnoreCase(name);
		if (productByName != null) {
			return productByName.stream().map(this::MapToResponse).toList();
		}
		throw new ResourceNotFoundException("Prouct not found");
	}

	@Override
	public List<ProductResponse> getMyProducts() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Product> myProducts = productRepository.findByEmail(authentication.getName());
		if (myProducts != null) {
			return myProducts.stream().map(this::MapToResponse).toList();
		}

		throw new ResourceNotFoundException("Prouct not found");

	}

	@Override
	public ProductResponse updateProduct(@Valid Long id, ProductRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<Product> updateProduct = productRepository.findById(id);
		System.out.println(updateProduct.isEmpty());
		if (!updateProduct.isEmpty()) {
			Product product = updateProduct.get();
			System.out.println("product emial" + product.getEmail());
			System.out.println("jwt emial" + authentication.getName().equals(product.getEmail()));
			if (product.getEmail().equalsIgnoreCase(authentication.getName())) {
				product = Product.builder().name(product.getName()).category(request.getCategory())
						.description(request.getDescription()).active(product.getActive())
						.createdAt(product.getCreatedAt()).imageUrl(request.getImageUrl()).price(request.getPrice())
						.email(authentication.getName()).offerPercentage(request.getOfferPercentage())
						.updateAt(product.getUpdateAt()).id(product.getId()).build();

				return MapToResponse(productRepository.save(product));
			} else {
				throw new RuntimeException("You are not authorized to update this product");
			}
		}
		return null;

	}

	@Override
	public boolean deleteProduct(@Valid Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<Product> product = productRepository.findById(id);
		System.out.println("inside deleteProduct");
		if (!product.isEmpty()) {
			System.out.println("inside deleteProduct if");
			if (authentication.getName().equalsIgnoreCase(product.get().getEmail())) {
				productRepository.deleteById(id);
				System.out.println("inside deleteProduct if true");
				return true;
			}
		}
		System.out.println("inside deleteProduct if false");
		return false;
	}

	@Override
	public List<ProductResponse> getMyProductsByEmail(String email) {
		// TODO Auto-generated method stub
		List<Product> myProducts = productRepository.findByEmail(email);
		if (myProducts != null) {
			return myProducts.stream().map(this::MapToResponse).toList();
		}

		throw new ResourceNotFoundException("Prouct not found");

	}

	@Override
	public Page<ProductResponse> searchProducts(ProductSearchRequest request) {

		Specification<Product> specification = Specification.where(null);

		specification = specification.and(ProductSpecification.hasName(request.getName()));
		specification = specification.and(ProductSpecification.hasCategory(request.getCategory()));

		Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

		Page<Product> products = productRepository.findAll(specification, pageable);

		List<ProductResponse> response = products.getContent().stream().map(this::productSearchResponse).toList();

		return new PageImpl<>(response, pageable, products.getTotalElements());
	}

	private ProductResponse productSearchResponse(Product product) {

		return ProductResponse.builder().id(product.getId()).category(product.getCategory()).email(product.getEmail())
				.createdAt(product.getCreatedAt()).name(product.getName()).imageUrl(product.getImageUrl())
				.offerPercentage(product.getOfferPercentage()).price(product.getPrice()).build();

	}

	@Override
	public List<Long> filterProducts(ProductRequest request) {
    Specification<Product> specification=Specification.where(null);
    specification=specification.and(ProductSpecification.hasName(request.getName()));
    specification=specification.and(ProductSpecification.hasCategory(request.getCategory()));
    return productRepository.findProductIds(request.getName(), request.getCategory());
   
	}

}
