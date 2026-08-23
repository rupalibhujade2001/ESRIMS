function AppTopbar({ viewMode, setViewMode, setPage, search, setSearch, buyerRequestCount, token, onLogout, onLogin, showNotice }) {
  const isSeller = viewMode === 'seller'
  return <header className="topbar">
    <button className="mobile-logo" onClick={() => setPage(isSeller ? 'overview' : 'buyer')}>*</button>
    <label className="search-box"><span>Q</span><input value={search} onChange={(event) => setSearch(event.target.value)} placeholder="Search bulk produce, farms or categories" /></label>
    <div className="top-actions"><button className="icon-button" onClick={() => showNotice(isSeller ? 'You have 3 supply alerts.' : 'Your saved enquiries are visible in Buyer desk.')}>!<b>{isSeller ? '3' : buyerRequestCount}</b></button><div className="role-toggle"><button className={isSeller ? 'selected' : ''} onClick={() => { setViewMode('seller'); setPage('overview') }}>Seller</button><button className={!isSeller ? 'selected' : ''} onClick={() => { setViewMode('buyer'); setPage('buyer') }}>Buyer</button></div><button className="auth-button" onClick={token ? onLogout : onLogin}>{token ? 'Log out' : 'Log in'}</button></div>
  </header>
}

export default AppTopbar
