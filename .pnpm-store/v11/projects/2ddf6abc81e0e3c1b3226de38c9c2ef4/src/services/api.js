const serviceUrl = (proxyPath, environmentName) =>
  import.meta.env.DEV ? proxyPath : import.meta.env[environmentName]

const authUrl = serviceUrl('/auth-api', 'VITE_AUTH_API_URL')
const productUrl = serviceUrl('/product-api', 'VITE_PRODUCT_API_URL')
const inventoryUrl = serviceUrl('/inventory-api', 'VITE_INVENTORY_API_URL')

async function request(url, options = {}) {
  const response = await fetch(url, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
  })

  if (!response.ok) {
    const body = await response.json().catch(() => ({}))
    throw new Error(body.message || `Request failed with status ${response.status}`)
  }

  return response.json()
}

export function login(credentials) {
  return request(`${authUrl}/auth/login`, {
    method: 'POST',
    body: JSON.stringify(credentials),
  })
}

export function getProducts() {
  return request(`${productUrl}/products`)
}

export function createProduct(product, token) {
  return request(`${productUrl}/products/createProduct`, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}` },
    body: JSON.stringify(product),
  })
}

export function updateProduct(productId, product, token) {
  return request(`${productUrl}/products/${productId}`, {
    method: 'PUT',
    headers: { Authorization: `Bearer ${token}` },
    body: JSON.stringify(product),
  })
}

export function deleteProduct(productId, product, token) {
  return request(`${productUrl}/products/${productId}`, {
    method: 'DELETE',
    headers: { Authorization: `Bearer ${token}` },
    // The current Spring controller declares a ProductRequest body for DELETE.
    body: JSON.stringify(product),
  })
}

export function getDashboard(token) {
  return request(`${inventoryUrl}/inventory/dashboard`, {
    headers: { Authorization: `Bearer ${token}` },
  })
}

export function getInventory(productId, token) {
  return request(`${inventoryUrl}/inventory/${productId}`, {
    headers: { Authorization: `Bearer ${token}` },
  })
}
