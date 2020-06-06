package com.syberry.sort;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ISchedulerImpl implements IScheduler {

    private Set<Task> visited = new HashSet<>();
    private List<Task> queue = new LinkedList<>();
    private List<Task> tasks;

    @Override
    public List<Task> schedule(List<Task> tasks) {
        this.tasks = tasks;
        visited.clear();
        queue.clear();

        tasks.forEach(this::visit);
        return queue;
    }

    private void visit(Task task) {
        if (!visited.contains(task)) {
            visited.add(task);

            if (task.getPredecessors().isEmpty() || isInQueue(task.getPredecessors())) {
                queue.add(task);
            } else {
                task.getPredecessors().forEach(it -> visit(mapToTask(it)));
                queue.add(task);
            }
        }
    }

    private Task mapToTask(String name) {
        return tasks.stream()
                .filter(it -> it.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    private boolean isInQueue(List<String> dependNames) {
        return queue.stream()
                .map(Task::getName)
                .collect(Collectors.toList())
                .containsAll(dependNames);
    }
}
