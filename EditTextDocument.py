names = [] #names of the files to be edited

for name in names:
    data = []
    with open("test_labyrinths/" + name + ".txt", "r") as file:
        for line in file:
            line = line.rstrip()
            symbols = []
            symbols.append("{")
            for char in line:
                if char == "0" or char == "1":
                    symbols.append(char)
                else:
                    symbols.append(", ")
            symbols.append("},\n")
            data.append(symbols)

            print(data)

        
    with open(name + "NEW.txt", "w") as file:                
        file.write("public void methodName() {\n")
        file.write("    int[][] labyrinth = {\n")
        for line in data:
            for char in line:
                file.write(str(char))
            
        file.write("};\n")
        file.write("}")