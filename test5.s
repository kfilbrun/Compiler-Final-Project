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
	movl	%EAX, %EDI
	movl	$5, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb6
main_bb4:
	movl	$5, %EAX
	cmpl	%EAX, %EDI
	jne	main_bb9
main_bb7:
	movl	$200, %EAX
	movl	%EAX, a(%RIP)
main_bb8:
	movl	$3, %EAX
	movl	%EAX, a(%RIP)
main_bb5:
	movl	$0, %EAX
	movl	%EAX, %R14D
	movl	$1, %EAX
	movl	%EAX, %R8D
	movl	$8, %EAX
	cmpl	%EAX, %R8D
	jg	main_bb11
main_bb10:
	movl	%R14D, %EAX
	addl	%R8D, %EAX
	movl	%EAX, %R14D
	movl	$1, %EDX
	movl	%R8D, %EAX
	addl	%EDX, %EAX
	movl	%EAX, %R8D
	cmpl	$0, %ECX
	je	main_bb10
main_bb11:
	movl	$3, %ECX
	movl	$0, %EDX
	movl	%R14D, %EAX
	idivl	%ECX, %EAX
	movl	$4, %EDX
	imull	%EDX, %EAX
	movl	%EAX, %R14D
	movl	%EDI, %EDX
	call	addThem
	movl	%R15D, %EAX
	addl	%R14D, %EAX
	movl	%EAX, %ESI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %ESI
	call	putchar
	movl	$0, %EAX
main_bb1:
	popq	%R15
	popq	%R14
	ret
main_bb9:
	movl	$21, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb8
main_bb6:
	movl	$4, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb5
