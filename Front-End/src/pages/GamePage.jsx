import {useEffect, useState} from 'react'
import {Link, useNavigate, useParams} from 'react-router'
import Board from '../components/Board.jsx'

const API_URL = import.meta.env.VITE_API_URL

const STATUS_LABELS = {
    PLACING_SHIPS: 'Posicionando navios',
    IN_PROGRESS: 'Em andamento',
    FINISHED: 'Encerrada'
}

function GamePage() {
    const {id} = useParams()
    const [game, setGame] = useState(null)
    const [message, setMessage] = useState('')
    const navigate = useNavigate()

    useEffect(() => {
        async function loadGame() {
            const token = localStorage.getItem('token')

            try {
                const response = await fetch(`${API_URL}/api/games/${id}`, {
                    headers: {Authorization: `Bearer ${token}`},
                })
                if (response.status === 200) {
                    const data = await response.json()
                    setGame(data)
                } else if (response.status === 401) {
                    localStorage.removeItem('token')
                    navigate('/login', {
                        replace: true,
                        state: {message: {text: 'Sua sessão expirou. Entre novamente.', type: 'info'}}
                    })
                } else if (response.status === 404 || response.status === 400) {
                    const problem = await response.json()
                    setMessage(problem.detail)
                } else {
                    setMessage('Não foi possível carregar a partida.')
                }
            } catch (error) {
                console.error(error)
                setMessage('Não foi possível conectar ao servidor. Tente novamente em instantes.')
            }
        }

        loadGame()
    }, [id, navigate])

    return (
        <main className="card card-wide">
            <h1>Partida contra o computador</h1>
            {!game && !message && <p className="message message-info">Carregando...</p>}
            {message && <p className="message message-error">{message}</p>}
            {game && (
                <>
                    <p>Situação: {STATUS_LABELS[game.status]}</p>
                    <p>{game.shipsCanTouch ? 'Navios podem encostar.' : 'Navios não podem encostar.'}</p>
                    <Board ships={game.fleet} />
                </>
            )}
            <p className="footer-text">
                <Link to="/home">Voltar para a área do jogador</Link>
            </p>
        </main>
    )
}

export default GamePage