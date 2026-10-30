import {isPlaced, SHIP_TYPES} from '../game/fleet.js'

const ORIENTATION_LABELS = {
    HORIZONTAL: 'Horizontal',
    VERTICAL: 'Vertical'
}

function ShipPicker({draft, selectedType, orientation, onSelect, onRotate}) {
    return (
        <div className="ship-picker">
            <ul className="ship-list">
                {Object.keys(SHIP_TYPES).map((type) => (
                    <li key={type}>
                        <button
                            type="button"
                            className={type === selectedType ? 'ship-option ship-option-selected' : 'ship-option'}
                            aria-pressed={type === selectedType}
                            disabled={isPlaced(draft, type)}
                            onClick={() => onSelect(type)}
                        >
                            {type === selectedType ? '▶ ' : ''}
                            {isPlaced(draft, type) ? '✓ ' : ''}
                            {SHIP_TYPES[type].name} ({SHIP_TYPES[type].size})
                        </button>
                    </li>
                ))}
            </ul>
            <button type="button" className="button" onClick={onRotate}>Girar (atual: {ORIENTATION_LABELS[orientation]})</button>
        </div>
    )
}

export default ShipPicker