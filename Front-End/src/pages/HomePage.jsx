import { useEffect, useState } from 'react'
const API_URL = import.meta.env.VITE_API_URL

function HomePage() {
    const [player, setPlayer] = useState(null)
    const [message, setMessage] = useState('')

    useEffect(() => {
        async function loadPlayer() {
            const token = localStorage.getItem('token')

            try {
                const response = await fetch(`${API_URL}/api/players/me`, {
                    headers: { Authorization: `Bearer ${token}` },
                })
                
                if (response.status === 200) {
                    const data = await response.json()
                    setPlayer(data)
                } else {
                    setMessage('Não foi possível carregar seus dados.')
                }
            } catch (error) {
                console.error(error)
                setMessage('Não foi possível conectar ao servidor. Tente novamente em instantes.')
            }
        }

        loadPlayer()
    }, [])

    return (
        <main>
            <h1>Área do jogador</h1>
            {player && <p>Bem-vindo, {player.username}</p>}

            <p>{message}</p>
        </main>
    )
}

export default HomePage