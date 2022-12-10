var lines = [String]()
while let line = readLine() {
    lines.append(line)
}

enum RPS: Int {
    case rock = 1
    case paper = 2
    case scissors = 3
}

enum RPSResult: Int {
    case win = 6
    case lose = 0
    case draw = 3
}

print(lines.map { line in
    let parts = line.split(separator: " ")
    let opponent = ["A": RPS.rock, "B": .paper, "C": .scissors][parts[0]]!
    let desired = ["X": RPSResult.lose, "Y": .draw, "Z": .win][parts[1]]!
    let you: RPS
    switch (desired, opponent) {
        case (.win, .rock), (.lose, .scissors), (.draw, .paper):
            you = .paper
        case (.win, .paper), (.lose, .rock), (.draw, .scissors):
            you = .scissors
        default:
            you = .rock
    }
    return desired.rawValue + you.rawValue
}.reduce(0, +))