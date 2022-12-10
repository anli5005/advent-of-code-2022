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

var sum = 0
for i in stride(from: 0, to: lines.count, by: 3) {
    let first = lines[i].split(separator: "").map { String($0) }
    let second = lines[i + 1].split(separator: "").map { String($0) }
    let third = lines[i + 2].split(separator: "").map { String($0) }
    let firstSet = Set(first)
    let secondSet = Set(second)
    let thirdSet = Set(third)
    let union = firstSet.intersection(secondSet).intersection(thirdSet)
    let result = union.map(\.priority).reduce(0, +)
    sum += result
}

print(sum)