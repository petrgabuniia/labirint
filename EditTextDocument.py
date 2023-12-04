names = ["10x10_multiRoad", "20x20_multiNo","30x30_oneRoad"]

for name in names:
    file = open("txtFiles/" + name + ".txt", "r")
    readFile = file.read()
    list = readFile.replace('\n', ' ').split(" ")
    line = ""

    number = int(name[0:2])

    for i in range(number):
        line += "{"
        for j in range(number):
            line += str(list[i*10+j]) + ", "
        line += "},\n"
    file.close()
    file = open("txtFiles/" + name + "NEW.txt", "w")
            
    file.write("public void methodName() {\n")
    file.write("    int[][] labyrinth = {\n")

    lines = line.split("\n")

    for data in lines:
        file.write("        ")
        file.write(data)
        file.write("\n")
    file.write("    };")