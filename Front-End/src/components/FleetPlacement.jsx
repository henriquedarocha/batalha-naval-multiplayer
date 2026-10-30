import {useState} from 'react'
import Board from './Board.jsx'
import ShipPicker from './ShipPicker.jsx'
import {firstPendingType, SHIP_TYPES} from '../game/fleet.js'

function FleetPlacement() {
    const [draft, setDraft] = useState([])
    const [selectedType, setSelectedType] = useState(firstPendingType([]))
    const [orientation, setOrientation] = useState('HORIZONTAL')

    function handleCellClick(row, col) {
        if (selectedType === null) {
            return
        }

        const ship = {type: selectedType, row: row, col: col, orientation: orientation}
        const newDraft = [...draft, ship]
        setDraft(newDraft)
        setSelectedType(firstPendingType(newDraft))
    }

    function handleSelect(type) {
        setSelectedType(type)
    }

    function handleRotate() {
        setOrientation(orientation === 'HORIZONTAL' ? 'VERTICAL' : 'HORIZONTAL')
    }

    return (
        <section className="fleet-placement">
            <ShipPicker
                draft={draft}
                selectedType={selectedType}
                orientation={orientation}
                onSelect={handleSelect}
                onRotate={handleRotate}
            />
            <Board ships={draft} onCellClick={handleCellClick}/>
            <p className="fleet-count">{draft.length} de {Object.keys(SHIP_TYPES).length} navios posicionados</p>
        </section>
    )
}

export default FleetPlacement