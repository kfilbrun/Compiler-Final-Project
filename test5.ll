(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Add_I [(r 3)]  [(r 1)(r 2)])
    (OPER 5 Mov [(r 0)]  [(r 3)])
    (OPER 6 Return []  [(r 0)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 1)]  [(i 5)])
    (OPER 5 Mov [(r 0)]  [(r 1)])
    (OPER 6 Mov [(r 2)]  [(i 5)])
    (OPER 7 EQ [(r 3)]  [(r 0)(r 2)])
    (OPER 8 BEQ []  [(r 3)(r 3)(bb 6)])
  )
  (BB 4
    (OPER 9 Mov [(r 4)]  [(i 3)])
    (OPER 10 Store []  [(r 4)(s a)])
    (OPER 11 Mov [(r 5)]  [(i 3)])
    (OPER 12 Store []  [(r 5)(s a)])
    (OPER 13 Jmp []  [(bb 6)])
  )
  (BB 5
    (OPER 14 Mov [(r 6)]  [(i 0)])
    (OPER 15 Mov [(r 0)]  [(r 6)])
    (OPER 16 Mov [(r 7)]  [(i 1)])
    (OPER 17 Mov [(r 0)]  [(r 7)])
  )
  (BB 7
    (OPER 18 Mov [(r 8)]  [(i 8)])
    (OPER 19 LTE [(r 9)]  [(r 0)(r 8)])
  )
  (BB 8
    (OPER 20 Add_I [(r 10)]  [(r 0)(r 0)])
    (OPER 21 Mov [(r 0)]  [(r 10)])
    (OPER 22 Mov [(r 11)]  [(i 1)])
    (OPER 23 Add_I [(r 12)]  [(r 0)(r 11)])
    (OPER 24 Mov [(r 0)]  [(r 12)])
  )
  (BB 9
    (OPER 25 Mov [(r 13)]  [(i 8)])
    (OPER 26 LTE [(r 14)]  [(r 0)(r 13)])
  )
  (BB 10
    (OPER 27 Mov [(r 15)]  [(i 3)])
    (OPER 28 Div_I [(r 16)]  [(r 0)(r 15)])
    (OPER 29 Mov [(r 0)]  [(r 16)])
    (OPER 30 Mov [(r 17)]  [(i 4)])
    (OPER 31 Mul_I [(r 18)]  [(r 0)(r 17)])
    (OPER 32 Mov [(r 0)]  [(r 18)])
    (OPER 33 Mov [(r 0)]  [(r 0)])
    (OPER 35 Mov [(r 19)]  [(i 0)])
    (OPER 34 Return []  [(r 19)])
  )
)
