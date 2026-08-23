import { useState } from 'react'

function ProductForm({ product, isCreating, onClose, onSubmit }) {
  const [form, setForm] = useState({
    name: product?.name || '',
    category: product?.category || 'Vegetables',
    price: product?.price || '',
    quantity: product?.availableQuantity ?? product?.quantity ?? '',
    description: product?.description || '',
    imageUrl: product?.imageUrl || '',
    offerPercentage: product?.offerPercentage || 0,
  })

  const updateField = (event) => setForm({ ...form, [event.target.name]: event.target.value })

  const submit = (event) => {
    event.preventDefault()
    onSubmit({
      ...form,
      price: Number(form.price),
      quantity: Number(form.quantity),
      offerPercentage: Number(form.offerPercentage || 0),
    })
  }

  return (
    <div className="modal-backdrop">
      <form className="product-modal" onSubmit={submit}>
        <button type="button" className="close-button" onClick={onClose}>x</button>
        <p className="eyebrow">WHOLESALE PRODUCT LISTING</p>
        <h2>{product ? 'Update your farm listing' : 'List a bulk harvest lot'}</h2>
        <p>
          {product ? 'Update the Product Service record. Quantity is required by the existing backend request model.' : (
            <>
              Publish the produce that your farm can supply to business buyers. This sends a secure request to <code>POST /products/createProduct</code>.
            </>
          )}
        </p>

        <div className="form-grid">
          <label>
            Produce name
            <input name="name" value={form.name} onChange={updateField} required placeholder="Organic tomatoes" />
          </label>
          <label>
            Category
            <select name="category" value={form.category} onChange={updateField}>
              <option>Vegetables</option>
              <option>Fruits</option>
              <option>Grains</option>
              <option>Leafy greens</option>
              <option>Dairy and eggs</option>
            </select>
          </label>
          <label>
            Wholesale price (INR / unit)
            <input name="price" type="number" min="1" value={form.price} onChange={updateField} required placeholder="68" />
          </label>
          <label>
            Available quantity
            <input name="quantity" type="number" min="1" value={form.quantity} onChange={updateField} required placeholder="100" />
          </label>
          <label>
            Offer percentage <small>(optional)</small>
            <input name="offerPercentage" type="number" min="0" max="100" value={form.offerPercentage} onChange={updateField} />
          </label>
          <label>
            Produce image <small>(optional)</small>
            <input name="imageUrl" type="url" value={form.imageUrl} onChange={updateField} placeholder="https://..." />
          </label>
        </div>

        <label className="full-field">
          Lot and delivery details
          <textarea name="description" value={form.description} onChange={updateField} required placeholder="Describe grade, harvest date, minimum order and delivery area." />
        </label>

        <button className="primary-button login-submit" disabled={isCreating}>
          {isCreating ? 'Saving listing...' : product ? 'Save product changes' : 'Publish wholesale listing'}
        </button>
      </form>
    </div>
  )
}

export default ProductForm
