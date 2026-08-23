function BuyerDashboard({ products, buyerRequests, setPage, onRequest }) {
  const featured = products.slice(0, 3)

  return (
    <div className="page-content buyer-page">
      <section className="buyer-hero">
        <div>
          <p className="eyebrow">WHOLESALE BUYER DESK</p>
          <h1>Source better,<br /><i>straight from farms.</i></h1>
          <p>Compare farm listings, shortlist bulk produce and keep your procurement enquiries together.</p>
          <button className="primary-button" onClick={() => setPage('marketplace')}>Browse farm listings</button>
        </div>
        <div className="buyer-hero-card">
          <span>Saved enquiry drafts</span>
          <strong>{buyerRequests.length}</strong>
          <p>These drafts are kept in this browser until an Order or Enquiry Service is added.</p>
          <button onClick={() => setPage('enquiries')}>View saved enquiries -&gt;</button>
        </div>
      </section>

      <section className="buyer-benefits">
        <article>
          <b>01</b>
          <h3>Direct sourcing</h3>
          <p>See the farm seller email attached to each product listing.</p>
        </article>
        <article>
          <b>02</b>
          <h3>Inventory aware</h3>
          <p>Signed-in users can see the available stock held by Inventory Service.</p>
        </article>
        <article>
          <b>03</b>
          <h3>Shortlist first</h3>
          <p>Save product enquiries before a future order workflow is connected.</p>
        </article>
      </section>

      <section className="section-title">
        <div>
          <p className="eyebrow">FEATURED FARM LISTINGS</p>
          <h2>Start your shortlist</h2>
        </div>
        <button className="text-button" onClick={() => setPage('marketplace')}>See all listings -&gt;</button>
      </section>

      <div className="buyer-featured-grid">
        {featured.map((product) => (
          <article className="buyer-featured-card" key={product.id}>
            <div>
              <span>{product.category}</span>
              <h3>{product.name}</h3>
              <p>{product.email ? `Seller: ${product.email}` : 'Direct farm listing'}</p>
            </div>
            <div>
              <strong>INR {product.price}</strong>
              <small> / {product.unit || 'kg'}</small>
              <button onClick={() => onRequest(product)}>Save enquiry</button>
            </div>
          </article>
        ))}
      </div>
    </div>
  )
}

export default BuyerDashboard
