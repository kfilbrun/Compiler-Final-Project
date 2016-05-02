(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Add_I [(r 4)]  [(r 1)(r 2)])
    (OPER 5 Mov [(r 3)]  [(r 4)])
    (OPER 6 Mov [(m RetReg)]  [(r 3)])
    (OPER 7 Jmp []  [(bb 4)])
  )
  (BB 4
    (OPER 8 Func_Exit []  [])
    (OPER 9 Return []  [(m RetReg)])
  )
)
