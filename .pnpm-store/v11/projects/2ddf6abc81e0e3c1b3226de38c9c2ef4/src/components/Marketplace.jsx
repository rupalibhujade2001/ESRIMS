import { productImages } from '../data/farmData'

function Marketplace({ products, showNotice, mode, currentUserEmail, onEdit, onDelete, onRequest }) {
  return (
    <div className="page-content marketplace">
      <section className="market-hero wholesale-market-hero">
        <div>
          <p className="eyebrow">{mode === 'seller' ? 'YOUR WHOLESALE LISTINGS' : 'FARM-TO-BUSINESS WHOLESALE'}</p>
          <h1>{mode === 'seller' ? <>Manage your<br /><i>farm listings.</i></> : <>Buy direct.<br /><i>Grow together.</i></>}</h1>
          <p>{mode === 'seller' ? 'Edit the products listed by your farm, keep their information accurate and remove listings you no longer sell.' : 'Browse farm listings with seller identity, product details and live available stock after login.'}</p>
          <div className="buyer-trust">
            <span>{mode === 'seller' ? 'Create, edit and delete' : 'Seller identity visible'}</span>
            <span>{mode === 'seller' ? 'Only your own listings' : 'Live stock after login'}</span>
            <span>Bulk pricing</span>
          </div>
        </div>
        <img src="https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=1200&q=85" alt="Fresh produce ready for wholesale" />
      </section>

      <div className="section-title wholesale-title">
        <div>
          <p className="eyebrow">{mode === 'seller' ? 'PRODUCT MANAGEMENT' : 'OPEN WHOLESALE LISTINGS'}</p>
          <h2>{mode === 'seller' ? 'Products listed by farms' : 'Available directly from farms'}</h2>
          <p>{mode === 'seller' ? 'Edit and delete controls appear on your own listings after you log in as the farmer who created them.' : 'Prices come from Product Service. Available stock is loaded from Inventory Service for signed-in users.'}</p>
        </div>
        <span>{products.length} active listings</span>
      </div>

      <div className="product-grid wholesale-grid">
        {products.map((product, index) => {
          const canManage = mode === 'seller' && currentUserEmail && product.email === currentUserEmail
          return (
            <article className="product-card wholesale-card" key={product.id}>
              <div className="product-image">
                <img src={product.imageUrl || productImages[index % productImages.length]} alt={product.name} />
                <span>{product.category}</span>
                <button className="detail-button" onClick={() => showNotice(product.description || 'No lot detail provided.')}>Details</button>
              </div>

              <div className="product-body">
                <div className="product-header-row">
                  <div>
                    <h3>{product.name}</h3>
                    <small>{product.email ? `Seller: ${product.email}` : 'Direct farm listing'}</small>
                  </div>
                  <strong>INR {product.price}</strong>
                </div>

                <p>{product.description || 'Fresh farm produce available in wholesale quantities.'}</p>

                <div className="product-meta">
                  <span>{product.availableQuantity ?? 'N/A'} units available</span>
                  <span>{product.grade || 'Grade A'}</span>
                </div>

                <div className="product-actions">
                  {canManage && (
                    <>
                      <button onClick={() => onEdit(product)}>Edit</button>
                      <button className="danger-button" onClick={() => onDelete(product)}>Delete</button>
                    </>
                  )}
                  {mode === 'buyer' && (
                    <button className="primary-button" onClick={() => onRequest(product)}>Save enquiry</button>
                  )}
                </div>
              </div>
            </article>
          )
        })}
      </div>
    </div>
  )
}

export default Marketplace
