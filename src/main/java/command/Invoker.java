package command;

import battleMap.BattleMap;

import java.util.Stack;

public class Invoker
{
    private Stack <AllCommands> undoStack = new Stack<>();
    private Stack <AllCommands> redoStack = new Stack<>();
    private BattleMap battleMap;

   public Invoker(BattleMap battleMap)
   {
       this.battleMap = battleMap;
   }

   public void invoke(AllCommands command){
       command.makeMove();
       undoStack.push(command);
       redoStack.clear();
   }

    public void undo() {
        if (undoStack.empty())
            return;

        AllCommands commands = undoStack.pop();
        commands.undo();
        redoStack.push(commands);
    }

    public void redo() {
        if (redoStack.empty())
            return;

        AllCommands commands = redoStack.pop();
        commands.redo();
        undoStack.push(commands);
    }

    public void move() {
        invoke(new OnlyOneMoveCommand(battleMap));
    }

    public void playToTheEnd()
    {
        invoke(new PlayToEndCommand(battleMap));
    }
}
