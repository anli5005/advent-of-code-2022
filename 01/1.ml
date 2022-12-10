let read_from_stdin (): string list =
  let rec loop acc =
    begin match (try Some (read_line ()) with End_of_file -> None) with
    | Some line -> loop (line::acc)
    | None -> List.rev acc
    end
  in
  loop []

let solve (things: string list) =
  let rec loop (things_left: string list) (current_sum: int) : int list =
    begin match things_left with
    | [] -> [current_sum]
    | ""::rest -> current_sum::(loop rest 0)
    | a::rest -> loop rest (current_sum + int_of_string a)
    end
  in
  List.sort (fun x y -> -compare x y) (loop things 0)

let answer = (solve (read_from_stdin ()))
;; print_string "Top 1 Elf: "
;; print_int (List.hd answer)
;; print_newline ()

;; print_string "Top 3 Elves: "
;; print_int ((List.nth answer 0) + (List.nth answer 1) + (List.nth answer 2))
;; print_newline ()