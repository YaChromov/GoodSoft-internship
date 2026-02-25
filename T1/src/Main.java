
void main(String[] args) {


  Scanner scanner = new Scanner(System.in);
  System.out.print("Путь к файлу: ");
  String srcInput = scanner.nextLine();
  System.out.print("Куда копировать: ");
  String dstInput = scanner.nextLine();

  Path src = Path.of(srcInput);
  Path dst = Path.of(dstInput);

  if (Files.notExists(src)) {
    System.err.println("Ошибка: Файл не найден!");
    return;
  }

  System.out.println("Cпособ: 1-IO, 2-NIO, 3-NIO2");
  int choice = scanner.nextInt();

  FileCopier copier;

  if (choice == 1) {
    copier = new IoFileCopier();
  } else if (choice == 2) {
    copier = new NioFileCopier();
  } else {
    copier = new Nio2FileCopier();
  }

  try {
    if (Files.exists(dst)) {
      System.out.printf("Файл [%s] уже существует. Перезаписать? (y/n): ", dst.getFileName());
      String answer = scanner.next();

      if (!answer.equalsIgnoreCase("y")) {
        System.out.println("Копирование отменено");
        return;
      }

    }
    copier.copy(src, dst);
    System.out.println("Файл успешно скопирован в: " + dst.toAbsolutePath());

  } catch (IOException e) {
    System.err.println("Ошибка при выполнении операции: " + e.getMessage());
  }
}