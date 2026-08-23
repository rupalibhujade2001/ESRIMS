export const productImages = [
  'https://images.unsplash.com/photo-1546470427-227c3f0c19fd?auto=format&fit=crop&w=900&q=85',
  'https://images.unsplash.com/photo-1500595046743-cd271d694d30?auto=format&fit=crop&w=900&q=85',
  'https://images.unsplash.com/photo-1576045057995-568f588f82fb?auto=format&fit=crop&w=900&q=85',
  'https://images.unsplash.com/photo-1506976785307-8732e854ad03?auto=format&fit=crop&w=900&q=85',
]

export const mockProducts = [
  { id: 1, name: 'Organic tomatoes', category: 'Vegetables', price: 68, unit: 'kg', imageUrl: productImages[0], availableQuantity: 380, description: 'Grade A tomatoes, ready for wholesale dispatch.' },
  { id: 2, name: 'Golden wheat', category: 'Grains', price: 42, unit: 'kg', imageUrl: productImages[1], availableQuantity: 1200, description: 'Premium wheat harvest suitable for bulk buyers.' },
  { id: 3, name: 'Fresh spinach', category: 'Leafy greens', price: 55, unit: 'bundle', imageUrl: productImages[2], availableQuantity: 260, description: 'Same-day harvest leafy greens for local business supply.' },
  { id: 4, name: 'Farm eggs', category: 'Dairy and eggs', price: 96, unit: 'dozen', imageUrl: productImages[3], availableQuantity: 420, description: 'Farm-packed eggs available in wholesale quantities.' },
]

export const mockDashboard = {
  totalProducts: 18,
  totalAvailableStock: 846,
  totalReservedStock: 74,
  lowStockProducts: 3,
  outOfStockProducts: 1,
  averageStock: 47,
  maximumStock: 184,
  minimumStock: 0,
  availableStockByCategory: { Vegetables: 388, Grains: 244, 'Leafy greens': 126, 'Dairy and eggs': 88 },
  lowStockItems: [
    { productId: 3, productName: 'Fresh spinach', category: 'Leafy greens', availableQuantity: 8, reservedQuantity: 4 },
    { productId: 17, productName: 'Red onions', category: 'Vegetables', availableQuantity: 4, reservedQuantity: 1 },
    { productId: 14, productName: 'Green peas', category: 'Vegetables', availableQuantity: 0, reservedQuantity: 0 },
  ],
}

export const sellerNavigation = [['overview', 'Seller dashboard'], ['marketplace', 'My listings'], ['inventory', 'Bulk stock']]
export const buyerNavigation = [['buyer', 'Buyer desk'], ['marketplace', 'Wholesale board'], ['enquiries', 'My enquiries']]

export const toProductRequest = (product) => ({
  name: product.name,
  category: product.category,
  price: Number(product.price),
  quantity: Number(product.quantity || product.availableQuantity || 1),
  description: product.description,
  imageUrl: product.imageUrl || '',
  offerPercentage: Number(product.offerPercentage || 0),
})
