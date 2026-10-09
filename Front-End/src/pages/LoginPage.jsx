import { useState } from 'react'
const API_URL = import.meta.env.VITE_API_URL

function LoginPage() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [message, setMessage] = useState('')
    const [errors, setErrors] = useState({})

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage('')
        setErrors({})

        const credentials = {
            email: email,
            password: password
        }

        try {
            const response = await fetch(`${API_URL}/api/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(credentials)
            })

            if (response.status === 200) {
                const data = await response.json()
                localStorage.setItem('token', data.token)
                setMessage('Login realizado com sucesso!')
                setEmail('')
                setPassword('')
            } else if (response.status === 400) {
                const problem = await response.json()
                setMessage(problem.detail)
                setErrors(problem.errors)
            } else if (response.status === 401) {
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
            <h1>Entrar</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="email">E-mail</label>
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
                    <button type="submit">Entrar</button>
                </div>
            </form>
            <p>{message}</p>
        </main>
    )
}

export default LoginPage