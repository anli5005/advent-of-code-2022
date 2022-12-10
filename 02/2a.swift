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
    let a = ["A": RPS.rock, "B": .paper, "C": .scissors][parts[0]]!
    let b = ["X": RPS.rock, "Y": .paper, "Z": .scissors][parts[1]]!
    let result: RPSResult
    switch (b, a) {
        case (.paper, .rock), (.scissors, .paper), (.rock, .scissors):
            result = .win
        case (.rock, .paper), (.paper, .scissors), (.scissors, .rock):
            result = .lose
        default:
            result = .draw
    }
    return result.rawValue + b.rawValue
}.reduce(0, +))