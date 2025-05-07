package org.eternity.adventure.game;

public class Game {
    private Player player;
    private CommandParser commandParser;
    private InputOutput io;
    private boolean running;

    public Game(InputOutput io) {
        this.player = new Player(
            new WorldMap(
                Size.with(2, 3),
                new Room(Position.of(0, 0), "샘", "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다."),
                new Room(Position.of(0, 1), "다리", "큰 강 위에 돌로 만든 커다란 다리가 있습니다."),
                new Room(Position.of(1, 1), "성", "용왕이 살고 있는 성에 도착했습니다."),
                new Room(Position.of(0, 2), "언덕", "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다."),
                new Room(Position.of(1, 2), "동굴", "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.")),
            Position.of(0, 2));
        this.commandParser = new CommandParser();
        this.io = io;
    }

    public void run() {
        welcome();
        play();
        farewell();
    }

    private void welcome() {
        showGreetings();
        showRoom();
        showHelp();
    }

    private void showGreetings() {
        io.showLine("환영합니다!");
    }

    private void showHelp() {
        io.showLine("다음 명령어를 사용할 수 있습니다.");
        io.showLine("go {north|east|south|west} - 이동, look - 보기, help - 도움말, quit - 게임 종료");
    }

    private void farewell() {
        io.showLine("\n게임을 종료합니다.");
    }

    private void play() {
        start();
        while (isRunning()) {
            String input = inputCommand();
            Command command = commandParser.parseCommand(input);
            executeCommand(command);
        }
    }

    private void executeCommand(Command command) {
        switch(command) {
            case Command.Move move -> tryMove(move.direction());
            case Command.Look() -> showRoom();
            case Command.Help() -> showHelp();
            case Command.Quit() -> stop();
            case Command.Unknown() -> showUnknownCommand();
        }
    }

    private void tryMove(Direction direction) {
        if (player.canMove(direction)) {
            player.move(direction);
            showRoom();
            return;
        }

        showBlocked();
    }

    private void showBlocked() {
        io.showLine("이동할 수 없습니다.");
    }

    public void showRoom() {
        io.showLine("당신은 [" + player.currentRoom().name() + "]에 있습니다.");
        io.showLine(player.currentRoom().description());
    }

    private boolean isRunning() {
        return running == true;
    }

    private String inputCommand() {
        showPrompt();
        return input();
    }

    private void start() {
        running = true;
    }

    private void stop() {
        this.running = false;
    }

    private void showUnknownCommand() {
        io.showLine("이해할 수 없는 명령어입니다.");
    }

    private String input() {
        return io.input();
    }

    private void showPrompt() {
        io.show("> ");
    }
}
