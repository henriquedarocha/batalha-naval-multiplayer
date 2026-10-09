import {useState} from 'react'
import {Link, useLocation, useNavigate} from 'react-router'

const API_URL = import.meta.env.VITE_API_URL

function LoginPage() {
    const location = useLocation()
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [message, setMessage] = useState(location.state?.message ?? null)
    const [errors, setErrors] = useState({})
    const navigate = useNavigate()

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage(null)
        setErrors({})

        const credentials = {
            email: email,
            password: password
        }

        try {
            const response = await fetch(`${API_URL}/api/auth/login`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(credentials)
            })

            if (response.status === 200) {
                const data = await response.json()
                localStorage.setItem('token', data.token)
                navigate('/home')
            } else if (response.status === 400) {
                const problem = await response.json()
                setMessage({text: problem.detail, type: 'error'})
                setErrors(problem.errors)
            } else if (response.status === 401) {
                const problem = await response.json()
                setMessage({text: problem.detail, type: 'error'})
            } else {
                setMessage({text: 'Erro inesperado', type: 'error'})
            }
        } catch (error) {
            console.error(error)
            setMessage({text: 'Não foi possível conectar ao servidor. Tente novamente em instantes.', type: 'error'})
        }
    }

    return (
        <main className="card">
            <h1>Entrar</h1>
            <form className="form" onSubmit={handleSubmit}>
                <div className="field">
                    <label htmlFor="email">E-mail</label>
                    <input
                        id="email"
                        type="email"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                    />
                    {errors.email && <p className="field-error">{errors.email}</p>}
                </div>
                <div className="field">
                    <label htmlFor="password">Senha</label>
                    <input
                        id="password"
                        type="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                    />
                    {errors.password && <p className="field-error">{errors.password}</p>}
                </div>
                <button className="button" type="submit">Entrar</button>
            </form>
            {message && <p className={`message message-${message.type}`}>{message.text}</p>}
            <p className="footer-text">
                Ainda não tem conta? <Link to="/register">Criar conta</Link>
            </p>
        </main>
    )
}

export default LoginPage