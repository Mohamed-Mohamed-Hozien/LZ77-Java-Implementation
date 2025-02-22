# **LZ77 Compression & Decompression in Java**

This project implements **LZ77 compression and decompression** in Java. It reads a text file, compresses it into LZ77 tags, and allows decompression back to its original form.

## **Files**

- **`LZ77.java`** → Contains the compression and decompression logic.
- **`LZ77Main.java`** → The main class for user interaction.
- **`main.txt`** → Sample input text file.

---

## **How It Works**

### **Compression (`compress()`)**

1. Reads an input text file.
2. Uses a **sliding window algorithm** (search + lookahead buffer).
3. Finds the longest match within the search buffer.
4. Generates **LZ77 tags** in the format:

   ```
   (position, length, nextChar)
   ```

5. Writes the compressed data to an output file.

### **Decompression (`decompress()`)**

1. Reads the compressed LZ77 tags.
2. Expands each tag back to its original form.
3. Writes the decompressed text to an output file.

---

## **How to Run**

### **Compile the Code**

```sh
javac LZ77.java LZ77Main.java
```

### **Run the Program**

```sh
java LZ77Main
```

### **Select an Option**

You'll be prompted with:
```
Choose an option: 1-Compress, 2-Decompress, 3-Exit
```

- **Enter `1`** → Compress a file.
- **Enter `2`** → Decompress a file.
- **Enter `3`** → Exit.

### **Example Usage**

#### **Compress a File**

```
Enter input file path: main.txt
Enter output file path: compress.txt
```

#### **Decompress a File**

```
Enter input file path: compress.txt
Enter output file path: decompressed.txt
```

---

## **Example Input & Output**

### **Input (`main.txt`)**

```
ABBABABAB ANANANNA
```

### **Compressed (`compress.txt`)**

```
(0,0,A) (0,0,B) (1,1,A) (2,2,B) (2,2, ) (3,1,N) (2,2,A) (2,1,N) (3,1,A)
```

### **Decompressed (`decompressed.txt`)**

```
ABBABABAB ANANANNA
```

**Output matches the original input!**

---

## **Error Handling**

- If the file **does not exist** or is **empty**, an error is shown.
- If the compressed file has **malformed tags**, they are **skipped** safely.
- **Spaces & newlines** are preserved in compression & decompression.

---

## **Notes**

- Compression works best for **repetitive text**.
- The `SEARCH_BUFFER_SIZE` is **256** characters.
- The `LOOKAHEAD_BUFFER_SIZE` is **15** characters.

---

## **Author**

- **Mohamed Hozien**
- **DSAI 325 - Information Theory**
- **Spring 2025**
