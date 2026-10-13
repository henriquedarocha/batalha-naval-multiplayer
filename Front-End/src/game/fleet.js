// Exceção consciente: mesmo valor do Coordinate.BOARD_SIZE do Java (o front não lê constantes Java).
export const BOARD_SIZE = 10

const ROW_LETTERS = 'ABCDEFGHIJ'

export const POSITIONS = Array.from({length: BOARD_SIZE}, (_, index) => index)

export const SHIP_TYPES = {
    CARRIER: {size: 5, name: 'Porta-aviões', initial: 'P'},
    BATTLESHIP: {size: 4, name: 'Encouraçado', initial: 'E'},
    CRUISER: {size: 3, name: 'Cruzador', initial: 'C'},
    SUBMARINE: {size: 3, name: 'Submarino', initial: 'S'},
    DESTROYER: {size: 2, name: 'Destróier', initial: 'D'}
}

export function rowLabel(row) {
    return ROW_LETTERS[row]
}

export function cellLabel(row, col) {
    return `${rowLabel(row)}${col + 1}`
}

export function shipCells(ship) {
    const cells = []

    for (let i = 0; i < SHIP_TYPES[ship.type].size; i++) {
        if (ship.orientation === 'HORIZONTAL') {
            cells.push({row: ship.row, col: ship.col + i})
        } else {
            cells.push({row: ship.row + i, col: ship.col})
        }
    }

    return cells
}