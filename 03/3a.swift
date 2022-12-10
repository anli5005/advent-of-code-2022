var lines = [String]()
while let line = readLine() {
    lines.append(line)
}

extension String {
    var priority: Int {
        if first!.isUppercase {
            return Int(first!.asciiValue! - "A".first!.asciiValue! + 27)
        } else {
            return Int(first!.asciiValue! - "a".first!.asciiValue! + 1)
        }
    }
}

print(lines.map { line in
    let items = line.split(separator: "").map { String($0) }
    let first = items[0..<(items.count / 2)]
    let second = items[(items.count / 2)..<items.count]
    let firstSet = Set(first)
    let secondSet = Set(second)
    let union = firstSet.intersection(secondSet)
    let result = union.map(\.priority).reduce(0, +)
    return result
}.reduce(0, +))