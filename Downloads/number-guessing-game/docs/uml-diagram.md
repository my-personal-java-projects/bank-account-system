# UML Class Diagram — Number Guessing Game

```
┌─────────────────────────────────────┐
│               <<main>>              │
│                Main                 │
├─────────────────────────────────────┤
│ + main(args: String[]) : void       │
└──────────────────┬──────────────────┘
                   │ creates
                   ▼
┌─────────────────────────────────────┐
│              GameUI                 │
├─────────────────────────────────────┤
│ - LOWER_BOUND  : int = 1            │
│ - UPPER_BOUND  : int = 100          │
│ - MAX_ATTEMPTS : int = 7            │
│ - scanner      : Scanner            │
├─────────────────────────────────────┤
│ + start()          : void           │
│ - playRound()      : void           │
│ - promptGuess()    : int            │
│ - askPlayAgain()   : boolean        │
│ - printBanner()    : void           │
└──────────────────┬──────────────────┘
                   │ creates & uses
                   ▼
┌─────────────────────────────────────┐
│               Game                  │
├─────────────────────────────────────┤
│ - secretNumber  : int               │
│ - maxAttempts   : int               │
│ - lowerBound    : int               │
│ - upperBound    : int               │
│ - attemptsUsed  : int               │
│ - isWon         : boolean           │
├─────────────────────────────────────┤
│ + Game(lower, upper, max)           │
│ + guess(number: int) : GuessResult  │
│ + isGameOver()   : boolean          │
│ + isWon()        : boolean          │
│ + getAttemptsUsed()  : int          │
│ + getMaxAttempts()   : int          │
│ + getLowerBound()    : int          │
│ + getUpperBound()    : int          │
│ + getSecretNumber()  : int          │
└──────┬───────────────────┬──────────┘
       │ returns           │ uses
       ▼                   ▼
┌──────────────────┐  ┌──────────────────────────┐
│   GuessResult    │  │     NumberGenerator       │
├──────────────────┤  ├──────────────────────────┤
│ - status         │  │  <<utility>>              │
│ - attemptsUsed   │  ├──────────────────────────┤
│ - maxAttempts    │  │ + generate(low, high: int)│
├──────────────────┤  │   : int   {static}        │
│ + getStatus()    │  └──────────────────────────┘
│ + getAttemptsUsed│
│ + getAttemptsRemaining()
│ + toString()     │
└──────────────────┘
       │
       │ contains
       ▼
┌──────────────────┐
│  <<enumeration>> │
│  Status          │
├──────────────────┤
│  TOO_LOW         │
│  TOO_HIGH        │
│  CORRECT         │
│  GAME_OVER       │
└──────────────────┘
```

## Relationships Summary

| From | To | Type | Description |
|---|---|---|---|
| Main | GameUI | Dependency | creates and calls `start()` |
| GameUI | Game | Dependency | creates a new `Game` per round |
| Game | NumberGenerator | Dependency | calls `generate()` in constructor |
| Game | GuessResult | Dependency | returns a `GuessResult` from `guess()` |
| GuessResult | Status | Composition | owns the `Status` enum |
