import {Navigate, Route, Routes} from 'react-router'
import LoginPage from './pages/LoginPage.jsx'
import RegisterPage from './pages/RegisterPage.jsx'
import HomePage from './pages/HomePage.jsx'
import NewGamePage from './pages/NewGamePage.jsx'
import GamePage from './pages/GamePage.jsx'

function App() {
    return (
        <Routes>
            <Route path="/" element={<Navigate to={localStorage.getItem('token') ? '/home' : '/login'} replace/>}/>

            <Route path="/register" element={<RegisterPage/>}/>
            <Route path="/login" element={<LoginPage/>}/>
            <Route path="/home" element={<HomePage/>}/>
            <Route path="/games/new" element={<NewGamePage/>}/>
            <Route path="/games/:id" element={<GamePage/>}/>

            <Route path="*" element={<p>Página não encontrada.</p>}/>
        </Routes>
    )
}

export default App