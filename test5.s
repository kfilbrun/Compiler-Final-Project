.data
.comm	a,4,4

.text
	.align 4
.globl  addThem
addThem:
addThem_bb2:
	movl	%EDI, %EAX
	movl	%ESI, %EDI
addThem_bb3:
	addl	%EDI, %EAX
addThem_bb4:
	ret
.globl  main
main:
main_bb2:
main_bb3:
	movl	$5, %EAX
	movl	%EAX, %EDI
	movl	$5, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb6
main_bb4:
	movl	$3, %EAX
	movl	%EAX, a(%RIP)
	movl	$4, %EAX
	movl	%EAX, a(%RIP)
main_bb6:
