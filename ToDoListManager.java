package JAVA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 할 일(To-Do List) 관리 프로그램
 * <p>
 * 사용자가 할 일을 추가, 삭제, 조회하고, 파일로 저장하거나 파일에서 불러올 수 있도록 제공합니다.
 * </p>
 * 
 * @author 작성자
 * @version 1.0
 */
public class ToDoListManager {

	/** 할 일을 저장하는 리스트 (ArrayList 사용). */
	private List<String> tasks;

	/** 중복된 작업을 방지하기 위한 유니크 작업 세트 (HashSet 사용). */
	private Set<String> uniqueTasks;

	/**
	 * 생성자: 할 일 목록과 유니크 작업 세트를 초기화합니다.
	 */
	public ToDoListManager() {
		tasks = new ArrayList<>();
		uniqueTasks = new HashSet<>();
	}

	/**
	 * 새로운 작업을 추가합니다. 중복된 작업은 추가되지 않습니다.
	 * 
	 * @param task 추가할 작업
	 */
	public void addTask(String task) {
		if (uniqueTasks.contains(task)) {
			System.out.println("이미 존재하는 작업입니다. 다른 작업을 추가하세요.");
		} else {
			tasks.add(task);
			uniqueTasks.add(task);
			System.out.println("작업 추가됨: " + task);
		}
	}

	/**
	 * 인덱스를 사용해 작업을 삭제합니다.
	 * 
	 * @param index 삭제할 작업의 인덱스 (0부터 시작)
	 */
	public void removeTask(int index) {
		if (index >= 0 && index < tasks.size()) {
			String removedTask = tasks.remove(index);
			uniqueTasks.remove(removedTask);
			System.out.println("작업 삭제됨: " + removedTask);
		} else {
			System.out.println("잘못된 인덱스입니다.");
		}
	}

	/**
	 * 현재 할 일 목록을 출력합니다.
	 */
	public void viewTasks() {
		System.out.println("\n할 일 목록:");
		if (tasks.isEmpty()) {
			System.out.println("현재 할 일이 없습니다.");
		} else {
			for (int i = 0; i < tasks.size(); i++) {
				System.out.println((i + 1) + ". " + tasks.get(i));
			}
		}
	}

	/**
	 * 할 일 목록을 파일에 저장합니다.
	 * 
	 * @param filename 저장할 파일명
	 */
	public void saveTasks(String filename) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			for (String task : tasks) {
				writer.write(task);
				writer.newLine();
			}
			System.out.println("작업이 " + filename + "에 저장되었습니다.");
		} catch (IOException e) {
			System.out.println("작업 저장 중 오류 발생: " + e.getMessage());
		}
	}

	/**
	 * 파일에서 할 일 목록을 불러옵니다.
	 * 
	 * @param filename 불러올 파일명
	 */
	public void loadTasks(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("파일을 찾을 수 없습니다: " + filename);
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			tasks.clear();
			uniqueTasks.clear();
			while ((line = reader.readLine()) != null) {
				tasks.add(line);
				uniqueTasks.add(line);
			}
			System.out.println("파일에서 작업이 로드되었습니다: " + filename);
		} catch (IOException e) {
			System.out.println("작업 로드 중 오류 발생: " + e.getMessage());
		}
	}

	/**
	 * 메인 메서드: 사용자에게 메뉴를 제공하고 기능을 실행합니다.
	 * 
	 * @param args 명령행 인수 (사용하지 않음)
	 */
	public static void main(String[] args) {
		ToDoListManager manager = new ToDoListManager();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n메뉴:");
			System.out.println("1. 작업 추가");
			System.out.println("2. 작업 보기");
			System.out.println("3. 작업 삭제");
			System.out.println("4. 작업 저장");
			System.out.println("5. 작업 불러오기");
			System.out.println("6. 종료");
			System.out.print("옵션을 선택하세요: ");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine(); // 줄바꿈 처리
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
				scanner.nextLine(); // 잘못된 입력 클리어
				continue;
			}

			switch (choice) {
			case 1:
				System.out.print("추가할 작업 입력: ");
				String task = scanner.nextLine();
				manager.addTask(task);
				break;
			case 2:
				manager.viewTasks();
				break;
			case 3:
				System.out.print("삭제할 작업 번호 입력: ");
				try {
					int index = scanner.nextInt() - 1;
					scanner.nextLine(); // 줄바꿈 처리
					manager.removeTask(index);
				} catch (Exception e) {
					System.out.println("잘못된 작업 번호입니다.");
					scanner.nextLine(); // 잘못된 입력 클리어
				}
				break;
			case 4:
				System.out.print("작업을 저장할 파일명 입력: ");
				String saveFile = scanner.nextLine();
				manager.saveTasks(saveFile);
				break;
			case 5:
				System.out.print("작업을 불러올 파일명 입력: ");
				String loadFile = scanner.nextLine();
				manager.loadTasks(loadFile);
				break;
			case 6:
				System.out.println("프로그램을 종료합니다.");
				scanner.close();
				return;
			default:
				System.out.println("잘못된 옵션입니다. 다시 시도하세요.");
			}
		}
	}
}