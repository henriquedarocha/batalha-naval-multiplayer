import { useState } from 'react'

function RegisterPage() {
    const [username, setUsername] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [message, setMessage] = useState('')
    const [errors, setErrors] = useState({})

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage('')
        setErrors({})

        const player = {
            username: username,
            email: email,
            password: password
        }

        const response = await fetch('http://localhost:8080/api/players', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(player)
        })

        if (response.status === 201) {
            setMessage('Cadastro criado com sucesso!')
            setUsername('')
            setEmail('')
            setPassword('')
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
        </main>
    )
}

export default RegisterPage