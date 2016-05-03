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
	pushq	%R14
	pushq	%R15
main_bb3:
	movl	$5, %EAX
	movl	%EAX, %ECX
	movl	$5, %EAX
	cmpl	%EAX, %ECX
	jne	main_bb6
main_bb4:
	movl	$3, %EAX
	movl	%EAX, a(%RIP)
main_bb5:
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	$3, %ESI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%ESI, %EAX
	movl	$4, %ESI
	imull	%ESI, %EAX
	movl	%EAX, %R15D
	movl	%ECX, %ESI
	call	addThem
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
	ret
main_bb6:
	movl	$4, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb5
