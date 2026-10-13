import {Fragment} from 'react'
import Cell from './Cell.jsx'
import {POSITIONS, rowLabel, shipCells} from '../game/fleet.js'

function Board({ships}) {
    const shipAt = {}
    for (const ship of ships) {
        for (const cell of shipCells(ship)) {
            shipAt[`${cell.row}-${cell.col}`] = ship.type
        }
    }

    return (
        <div className="board" role="group" aria-label="Seu tabuleiro">
            <span aria-hidden="true"></span>
            {POSITIONS.map((col) => (
                <span key={col} className="board-label" aria-hidden="true">{col + 1}</span>
            ))}

            {POSITIONS.map((row) => (
                <Fragment key={row}>
                    <span className="board-label" aria-hidden="true">{rowLabel(row)}</span>
                    {POSITIONS.map((col) => (
                        <Cell key={col} row={row} col={col} shipType={shipAt[`${row}-${col}`] ?? null}/>
                    ))}
                </Fragment>
            ))}
        </div>
    )
}

export default Board