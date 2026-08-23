import { useState } from 'react'

function LoginModal({ role, setRole, isLoggingIn, onClose, onSubmit }) {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const submit = (event) => {
    event.preventDefault()
    onSubmit({ email, password, role })
  }

  return (
    <div className="modal-backdrop">
      <form className="login-modal" onSubmit={submit}>
        <button type="button" className="close-button" onClick={onClose}>x</button>
        <p className="eyebrow">FARMLINK TRADE ACCESS</p>
        <h2>Welcome back</h2>
        <p>Log in as the Farmer or Admin role registered in Auth Service.</p>

        <label>
          Email
          <input type="email" value={email} onChange={(event) => setEmail(event.target.value)} required placeholder="farmer@example.com" />
        </label>

        <label>
          Password
          <input type="password" value={password} onChange={(event) => setPassword(event.target.value)} required placeholder="Enter your password" />
        </label>

        <div className="login-roles">
          <button type="button" className={role === 'FARMER' ? 'selected' : ''} onClick={() => setRole('FARMER')}>Farm seller</button>
          <button type="button" className={role === 'ADMIN' ? 'selected' : ''} onClick={() => setRole('ADMIN')}>Platform admin</button>
        </div>

        <button className="primary-button login-submit" disabled={isLoggingIn}>
          {isLoggingIn ? 'Logging in...' : 'Log in to trade'}
        </button>
      </form>
    </div>
  )
}

export default LoginModal
