function BuyerEnquiries({ buyerRequests, onClear, setPage }) {
  return (
    <div className="page-content buyer-page">
      <section className="inventory-heading">
        <div>
          <p className="eyebrow">BUYER WORKSPACE</p>
          <h1>Your saved <i>enquiries.</i></h1>
          <p>These are browser-only procurement drafts. They are not sent to a seller because the current backend has no enquiry or order endpoint.</p>
        </div>
        {buyerRequests.length > 0 && (
          <button className="secondary-button" onClick={onClear}>Clear drafts</button>
        )}
      </section>

      <div className="panel enquiry-panel">
        {buyerRequests.length ? (
          buyerRequests.map((request) => (
            <article className="enquiry-row" key={request.id}>
              <div>
                <p className="eyebrow">{request.status}</p>
                <h3>{request.productName}</h3>
                <p>Seller: {request.seller} · Saved {request.createdAt}</p>
              </div>
              <div>
                <strong>INR {request.price}</strong>
                <small> listed unit price</small>
              </div>
            </article>
          ))
        ) : (
          <div className="empty-state">
            You have no saved enquiries yet.<br />
            <button className="primary-button" onClick={() => setPage('marketplace')}>Browse wholesale listings</button>
          </div>
        )}
      </div>
    </div>
  )
}

export default BuyerEnquiries
