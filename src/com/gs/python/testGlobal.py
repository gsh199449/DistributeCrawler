a = 123
def fun():
    global a
    a = 'hello'
    print id(a)
fun()
print id(a)