import {cellLabel, SHIP_TYPES} from '../game/fleet.js'

function Cell({row, col, shipType, onCellClick}) {
    const label = cellLabel(row, col)
    const ship = shipType ? SHIP_TYPES[shipType] : null

    return (
        <button
            type="button"
            className={ship ? 'cell cell-ship' : 'cell'}
            aria-label={ship ? `${label}, ${ship.name}` : `${label}, água`}
            onClick={() => onCellClick?.(row, col)}
        >
            {ship && ship.initial}
        </button>
    )
}

export default Cell