import {useState} from 'react'
import {Link, useNavigate} from 'react-router'

const API_URL = import.meta.env.VITE_API_URL

function NewGamePage() {
    const [shipsCanTouch, setShipsCanTouch] = useState(null)
    const [message, setMessage] = useState('')
    const navigate = useNavigate()

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage('')

        const game = {
            mode: 'SINGLE_PLAYER',
            shipsCanTouch: shipsCanTouch
        }

        const token = localStorage.getItem('token')

        try {
            const response = await fetch(`${API_URL}/api/games`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                },
                body: JSON.stringify(game)
            })

            if (response.status === 201) {
                const data = await response.json()
                navigate(`/games/${data.id}`)
            } else if (response.status === 401 || response.status === 404) {
                localStorage.removeItem('token')
                navigate('/login', {
                    replace: true,
                    state: {message: {text: 'Sua sessão expirou. Entre novamente.', type: 'info'}}
                })
            } else if (response.status === 400) {
                const problem = await response.json()
                setMessage(problem.detail)
            } else {
                setMessage('Não foi possível criar a partida.')
            }
        } catch (error) {
            console.error(error)
            setMessage('Não foi possível conectar ao servidor. Tente novamente em instantes.')
        }
    }

    return (
        <main className="card">
            <h1>Nova partida</h1>
            <form className="form" onSubmit={handleSubmit}>
                <fieldset className="choice-group">
                    <legend>Navios podem encostar uns nos outros?</legend>
                    <label className="choice">
                        <input
                            type="radio"
                            name="shipsCanTouch"
                            checked={shipsCanTouch === true}
                            onChange={() => setShipsCanTouch(true)}
                            required
                        />
                        Sim
                    </label>
                    <label className="choice">
                        <input
                            type="radio"
                            name="shipsCanTouch"
                            checked={shipsCanTouch === false}
                            onChange={() => setShipsCanTouch(false)}
                        />
                        Não (é preciso ao menos uma casa de distância, inclusive na diagonal)
                    </label>
                </fieldset>
                <button className="button" type="submit">Criar partida</button>
            </form>
            {message && <p className="message message-error">{message}</p>}
            <p className="footer-text">
                <Link to="/home">Voltar para a área do jogador</Link>
            </p>
        </main>
    )
}

export default NewGamePage