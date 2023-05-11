import levenshtein from 'fast-levenshtein';
const data = require('../assets/medicine.json');

export function findClosestMatch(inputValue) {
    if (!inputValue || !data.length) {
        return null;
    }

    let closestMatch = null;
    let minDistance = Infinity;

    for (const item of data) {
        const distance = levenshtein.get(inputValue, item.NOM_COURT);
        if (distance < minDistance) {
            minDistance = distance;
            closestMatch = item.NOM_COURT;
        }
    }

    return closestMatch;
}