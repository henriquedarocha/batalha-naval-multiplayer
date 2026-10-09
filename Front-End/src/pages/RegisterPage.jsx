import { useState } from 'react'
import { Link, useNavigate } from 'react-router'
const API_URL = import.meta.env.VITE_API_URL

function RegisterPage() {
    const [username, setUsername] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [message, setMessage] = useState('')
    const [errors, setErrors] = useState({})
    const navigate = useNavigate()

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage('')
        setErrors({})

        const player = {
            username: username,
            email: email,
            password: password
        }

        try {
            const response = await fetch(`${API_URL}/api/players`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(player)
            })

            if (response.status === 201) {
                navigate('/login', { state: { message: 'Cadastro criado com sucesso! Agora é só fazer login.' } })
            } else if (response.status === 400) {
                const problem = await response.json()
                setMessage(problem.detail)
                setErrors(problem.errors)
            } else if (response.status === 409) {
                const problem = await response.json()
                setMessage(problem.detail)
            } else {
                setMessage('Erro inesperado')
            }
        } catch (error) {
            console.error(error)
            setMessage('Não foi possível conectar ao servidor. Tente novamente em instantes.')
        }
    }

    return (
        <main>
            <h1>Criar conta</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Nome de usuário</label>
                    <input
                        id="username"
                        type="text"
                        value={username}
                        onChange={(event) => setUsername(event.target.value)}
                    />
                    {errors.username && <p>{errors.username}</p>}
                </div>
                <div>
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        type="email"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                    />
                    {errors.email && <p>{errors.email}</p>}
                </div>
                <div>
                    <label htmlFor="password">Senha</label>
                    <input
                        id="password"
                        type="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                    />
                    {errors.password && <p>{errors.password}</p>}
                </div>
                <div>
                    <button type="submit">Cadastrar</button>
                </div>
            </form>
            <p>{message}</p>
            <p>
                Já tem uma conta? <Link to="/login">Entrar</Link>
            </p>
        </main>
    )
}

export default RegisterPage