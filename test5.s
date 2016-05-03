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
addThem_bb1:
	ret
.globl  main
main:
main_bb2:
	pushq	%R13
	pushq	%R14
	pushq	%R15
main_bb3:
	movl	$5, %EAX
	movl	%EAX, %R13D
	movl	$5, %EAX
	cmpl	%EAX, %R13D
	jne	main_bb6
main_bb4:
	movl	$5, %EAX
	cmpl	%EAX, %R13D
	jne	main_bb9
main_bb7:
	movl	$200, %EAX
	movl	%EAX, a(%RIP)
main_bb8:
	movl	$3, %EAX
	movl	%EAX, a(%RIP)
main_bb5:
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %R14D
	movl	$8, %EAX
	cmpl	%EAX, %R14D
	jg	main_bb11
main_bb10:
	movl	%R15D, %EAX
	addl	%R14D, %EAX
	movl	%EAX, %R15D
	movl	$1, %EDI
	movl	%R14D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %R14D
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$48, %EDI
	movl	%R14D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$8, %EAX
	cmpl	%EAX, %R14D
	jle	main_bb10
main_bb11:
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$3, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	$4, %EDI
	imull	%EDI, %EAX
	movl	%EAX, %R15D
	movl	%R13D, %ESI
	movl	a(%RIP), %EAX
	movl	%EAX, %EDI
	call	addThem
	movl	%EAX, %R14D
	movl	a(%RIP), %EAX
	movl	$48, %EDI
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$7, %EAX
	movl	%EAX, a(%RIP)
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	a(%RIP), %EAX
	movl	$48, %EDI
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$48, %EDI
	movl	%R13D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$48, %EDI
	movl	%R14D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	%R14D, %EAX
	addl	%R15D, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
main_bb1:
	popq	%R15
	popq	%R14
	popq	%R13
	ret
main_bb9:
	movl	$21, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb8
main_bb6:
	movl	$4, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb5
