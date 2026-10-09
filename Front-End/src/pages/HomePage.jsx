import {useEffect, useState} from 'react'
import {useNavigate} from 'react-router'

const API_URL = import.meta.env.VITE_API_URL

function HomePage() {
    const [player, setPlayer] = useState(null)
    const [message, setMessage] = useState('')
    const navigate = useNavigate()

    useEffect(() => {
        async function loadPlayer() {
            const token = localStorage.getItem('token')

            try {
                const response = await fetch(`${API_URL}/api/players/me`, {
                    headers: {Authorization: `Bearer ${token}`},
                })

                if (response.status === 200) {
                    const data = await response.json()
                    setPlayer(data)
                } else if (response.status === 401 || response.status === 404) {
                    localStorage.removeItem('token')
                    navigate('/login', {
                        replace: true,
                        state: {message: {text: 'Sua sessão expirou. Entre novamente.', type: 'info'}}
                    })
                } else {
                    setMessage('Não foi possível carregar seus dados.')
                }
            } catch (error) {
                console.error(error)
                setMessage('Não foi possível conectar ao servidor. Tente novamente em instantes.')
            }
        }

        loadPlayer()
    }, [navigate])

    function handleLogout() {
        localStorage.removeItem('token')
        navigate('/login', {replace: true, state: {message: {text: 'Você saiu da sua conta.', type: 'info'}}})
    }

    return (
        <main className="card">
            <h1>Área do jogador</h1>
            {!player && !message && <p className="message message-info">Carregando...</p>}
            {player && <p>Bem-vindo, {player.username}</p>}

            {message && <p className="message message-error">{message}</p>}
            <button className="button" type="button" onClick={handleLogout}>Sair</button>
        </main>
    )
}

export default HomePage