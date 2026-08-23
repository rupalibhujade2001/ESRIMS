function AppSidebar({ viewMode, page, setPage, navigation, token, accountLabel, showNotice }) {
  const isSeller = viewMode === 'seller'
  return <aside className="sidebar">
    <a className="brand" href="#top" onClick={() => setPage(isSeller ? 'overview' : 'buyer')}><span className="brand-mark">*</span><span>farm<span>link</span></span></a>
    <div className="farm-switcher"><div className="avatar">{isSeller ? 'SK' : 'BY'}</div><div><strong>{isSeller ? "Samadhan's Farm" : 'Buyer workspace'}</strong><small>{isSeller ? (token ? 'Verified seller account' : 'Demo seller account') : 'Source direct from farms'}</small></div><span className="chevron">v</span></div>
    <nav>
      <p className="nav-label">{isSeller ? 'Farm business' : 'Procurement'}</p>
      {navigation.map(([key, label]) => <button className={page === key ? 'nav-item active' : 'nav-item'} key={key} onClick={() => setPage(key)}><span>{key === 'overview' ? '#' : key === 'marketplace' ? '+' : '='}</span>{label}</button>)}
      <p className="nav-label">Trade</p>
      <button className="nav-item" onClick={() => isSeller ? showNotice('Bulk order management needs an Order Service endpoint.') : setPage('enquiries')}><span>o</span>{isSeller ? 'Bulk orders' : 'Saved enquiries'} {isSeller && <em>Soon</em>}</button>
      <button className="nav-item" onClick={() => isSeller ? showNotice('Buyer enquiries need an Order or Enquiry Service endpoint.') : setPage('marketplace')}><span>+</span>{isSeller ? 'Buyer enquiries' : 'Find farm listings'} {isSeller && <em>Soon</em>}</button>
    </nav>
    <div className="sidebar-bottom"><button className="help-button" onClick={() => showNotice('Need help? Your trade support centre will open here.')}>? <span>Trade support</span></button><div className="profile-row"><div className="avatar avatar-dark">{isSeller ? 'SK' : 'BY'}</div><div><strong>{isSeller ? 'Samadhan Kadam' : 'Wholesale buyer'}</strong><small>{isSeller ? accountLabel : 'Buyer view'}</small></div><span>...</span></div></div>
  </aside>
}

export default AppSidebar
