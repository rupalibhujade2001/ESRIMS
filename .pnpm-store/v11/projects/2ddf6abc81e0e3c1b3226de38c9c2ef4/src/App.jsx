import { useEffect, useMemo, useState } from 'react'
import { createProduct, deleteProduct, getDashboard, getInventory, getProducts, login, updateProduct } from './services/api'
import './App.css'
import AppSidebar from './components/AppSidebar'
import AppTopbar from './components/AppTopbar'
import BuyerDashboard from './components/BuyerDashboard'
import BuyerEnquiries from './components/BuyerEnquiries'
import Inventory from './components/Inventory'
import LoginModal from './components/LoginModal'
import Marketplace from './components/Marketplace'
import ProductForm from './components/ProductForm'
import SellerDashboard from './components/SellerDashboard'
import { buyerNavigation, mockDashboard, mockProducts, productImages, sellerNavigation, toProductRequest } from './data/farmData'

function App() {
  const [page, setPage] = useState('overview')
  const [search, setSearch] = useState('')
  const [role, setRole] = useState('ADMIN')
  const [notice, setNotice] = useState('')
  const [products, setProducts] = useState(mockProducts)
  const [dashboard, setDashboard] = useState(mockDashboard)
  const [token, setToken] = useState(() => localStorage.getItem('farmfresh-token') || '')
  const [isLiveDashboard, setIsLiveDashboard] = useState(false)
  const [showLogin, setShowLogin] = useState(false)
  const [isLoggingIn, setIsLoggingIn] = useState(false)
  const [showProductForm, setShowProductForm] = useState(false)
  const [isCreatingProduct, setIsCreatingProduct] = useState(false)
  const [editingProduct, setEditingProduct] = useState(null)
  const [viewMode, setViewMode] = useState('seller')
  const [currentUserEmail, setCurrentUserEmail] = useState(() => localStorage.getItem('farmfresh-email') || '')
  const [buyerRequests, setBuyerRequests] = useState(() => {
    try { return JSON.parse(localStorage.getItem('farmfresh-buyer-requests') || '[]') } catch { return [] }
  })
  const accountLabel = role === 'FARMER' ? 'Farm seller' : 'Platform administrator'
  const navigation = viewMode === 'seller' ? sellerNavigation : buyerNavigation

  const visibleProducts = useMemo(() => products.filter((product) =>
    product.name.toLowerCase().includes(search.toLowerCase()) ||
    product.category.toLowerCase().includes(search.toLowerCase()),
  ), [products, search])

  const showNotice = (message) => {
    setNotice(message)
    window.setTimeout(() => setNotice(''), 2600)
  }

  const loadProducts = async (accessToken = token) => {
    try {
      const apiProducts = await getProducts()
      const productsWithInventory = await Promise.all(apiProducts.map(async (product) => {
        if (!accessToken) return product
        try {
          const inventory = await getInventory(product.id, accessToken)
          return { ...product, availableQuantity: inventory.AvailableQUantity ?? 0, reservedQuantity: inventory.reservedQuantity ?? 0 }
        } catch {
          return product
        }
      }))
      setProducts(productsWithInventory.map((product, index) => ({
        ...product,
        imageUrl: product.imageUrl || productImages[index % productImages.length],
        unit: product.unit || 'kg',
        availableQuantity: product.availableQuantity ?? null,
      })))
    } catch {
      setProducts(mockProducts)
    }
  }

  const loadDashboard = async (accessToken) => {
    try {
      const apiDashboard = await getDashboard(accessToken)
      setDashboard(apiDashboard)
      setIsLiveDashboard(true)
    } catch {
      setDashboard(mockDashboard)
      setIsLiveDashboard(false)
    }
  }

  useEffect(() => { loadProducts(token) }, [token])
  useEffect(() => { if (token) loadDashboard(token) }, [token])

  const handleLogin = async (credentials) => {
    setIsLoggingIn(true)
    try {
      const response = await login(credentials)
      localStorage.setItem('farmfresh-token', response.token)
      localStorage.setItem('farmfresh-email', response.email)
      setToken(response.token)
      setCurrentUserEmail(response.email)
      setRole(response.role || credentials.role)
      setShowLogin(false)
      showNotice(`Welcome back, ${response.email}. Live dashboard loading.`)
    } catch (error) {
      showNotice(error.message || 'Login failed. Check your email, password, and role.')
    } finally {
      setIsLoggingIn(false)
    }
  }

  const logout = () => {
    localStorage.removeItem('farmfresh-token')
    localStorage.removeItem('farmfresh-email')
    setToken('')
    setCurrentUserEmail('')
    setDashboard(mockDashboard)
    setIsLiveDashboard(false)
    showNotice('You are logged out. Demo data is visible again.')
  }

  const openProductForm = () => {
    if (!token) {
      showNotice('Log in before creating a product.')
      setShowLogin(true)
      return
    }
    if (role !== 'FARMER') {
      showNotice('Only a FARMER account can publish a product listing.')
      return
    }
    setEditingProduct(null)
    setShowProductForm(true)
  }

  const handleSaveProduct = async (product) => {
    setIsCreatingProduct(true)
    try {
      if (editingProduct) {
        await updateProduct(editingProduct.id, product, token)
      } else {
        await createProduct(product, token)
      }
      await loadProducts()
      setShowProductForm(false)
      setEditingProduct(null)
      setPage('marketplace')
      showNotice(editingProduct ? 'Product listing updated.' : 'Bulk listing published. Inventory will be created through Kafka shortly.')
      if (!editingProduct) window.setTimeout(() => loadDashboard(token), 1500)
    } catch (error) {
      showNotice(error.message || 'Product could not be saved.')
    } finally {
      setIsCreatingProduct(false)
    }
  }

  const openEditProduct = (product) => {
    setEditingProduct(product)
    setShowProductForm(true)
  }

  const handleDeleteProduct = async (product) => {
    if (!window.confirm(`Delete the listing for ${product.name}? This cannot be undone.`)) return
    try {
      await deleteProduct(product.id, toProductRequest(product), token)
      await loadProducts()
      showNotice(`${product.name} was deleted.`)
    } catch (error) {
      showNotice(error.message || 'Product could not be deleted.')
    }
  }

  const createBuyerRequest = (product) => {
    const request = { id: `${product.id}-${Date.now()}`, productId: product.id, productName: product.name, seller: product.email || 'Farm seller', price: product.price, createdAt: new Date().toLocaleDateString(), status: 'Draft enquiry' }
    const updatedRequests = [request, ...buyerRequests]
    setBuyerRequests(updatedRequests)
    localStorage.setItem('farmfresh-buyer-requests', JSON.stringify(updatedRequests))
    showNotice(`Enquiry draft added for ${product.name}.`)
    setPage('enquiries')
  }

  return (
    <div className="app-shell">
      <AppSidebar
        viewMode={viewMode}
        page={page}
        setPage={setPage}
        navigation={navigation}
        token={token}
        accountLabel={accountLabel}
        showNotice={showNotice}
      />

      <main id="top">
        <AppTopbar
          viewMode={viewMode}
          setViewMode={setViewMode}
          setPage={setPage}
          search={search}
          setSearch={setSearch}
          buyerRequestCount={buyerRequests.length}
          token={token}
          onLogout={logout}
          onLogin={() => setShowLogin(true)}
          showNotice={showNotice}
        />

        {notice && <div className="toast">Done - {notice}</div>}

        {viewMode === 'seller' && page === 'overview' && (
          <SellerDashboard dashboard={dashboard} isLiveDashboard={isLiveDashboard} setPage={setPage} showNotice={showNotice} onAddProduct={openProductForm} />
        )}
        {viewMode === 'seller' && page === 'marketplace' && (
          <Marketplace products={visibleProducts} showNotice={showNotice} mode="seller" currentUserEmail={currentUserEmail} onEdit={openEditProduct} onDelete={handleDeleteProduct} />
        )}
        {viewMode === 'seller' && page === 'inventory' && (
          <Inventory dashboard={dashboard} isLiveDashboard={isLiveDashboard} showNotice={showNotice} />
        )}

        {viewMode === 'buyer' && page === 'buyer' && (
          <BuyerDashboard products={visibleProducts} buyerRequests={buyerRequests} setPage={setPage} onRequest={createBuyerRequest} />
        )}
        {viewMode === 'buyer' && page === 'marketplace' && (
          <Marketplace products={visibleProducts} showNotice={showNotice} mode="buyer" onRequest={createBuyerRequest} />
        )}
        {viewMode === 'buyer' && page === 'enquiries' && (
          <BuyerEnquiries buyerRequests={buyerRequests} onClear={() => { setBuyerRequests([]); localStorage.removeItem('farmfresh-buyer-requests') }} setPage={setPage} />
        )}
      </main>

      {showLogin && <LoginModal role={role} setRole={setRole} isLoggingIn={isLoggingIn} onClose={() => setShowLogin(false)} onSubmit={handleLogin} />}
      {showProductForm && <ProductForm product={editingProduct} isCreating={isCreatingProduct} onClose={() => { setShowProductForm(false); setEditingProduct(null) }} onSubmit={handleSaveProduct} />}
    </div>
  )
}

export default App
