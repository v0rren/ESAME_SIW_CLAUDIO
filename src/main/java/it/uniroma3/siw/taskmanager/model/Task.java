package it.uniroma3.siw.taskmanager.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A Task is a unitary activity managed by the TaskManager.
 * It is generated and owned by a specific User within the context of a specific Project.
 * The task is contained in the Project and is visible to whoever has visibility over its Project.
 * The Task can be marked as "completed".
 */
@Entity
public class Task {

	/**
     * Unique identifier for this Task
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Name for this task
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Description for this task
     */
    @Column
    private String description;

    /**
     * Project for this task
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;
    
    @ManyToOne
    private User assignedTo;
    /**
     * Boolean flag specifying whether this Task is completed or not
     */
    @Column(nullable = false)
    private boolean completed;

    /**
     * Timestamp for the instant this Task was created/loaded into the DB
     */
    @Column(updatable = false, nullable = false)
    private LocalDateTime creationTimestamp;

    /**
     * Timestamp for the last update of this Task into the DB
     */
    @Column(nullable = false)
    private LocalDateTime lastUpdateTimestamp;
    
    
    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
    private List<ProjectTag> tags;   //Lista di tag del task
    
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    
    public Task() {}

    public Task(String name,
                String description,
                boolean completed) {
        this.name = name;
        this.description = description;
        this.completed = completed;
    }

    /**
     * This method initializes the creationTimestamp and lastUpdateTimestamp of this User to the current instant.
     * This method is called automatically just before the User is persisted thanks to the @PrePersist annotation.
     */
    @PrePersist
    protected void onPersist() {
        this.creationTimestamp = LocalDateTime.now();
        this.lastUpdateTimestamp = LocalDateTime.now();
    }

    /**
     * This method updates the lastUpdateTimestamp of this User to the current instant.
     * This method is called automatically just before the User is updated thanks to the @PreUpdate annotation.
     */
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateTimestamp = LocalDateTime.now();
    }


    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public List<ProjectTag> getTags() {
		return tags;
	}

	public void setTags(List<ProjectTag> tags) {
		this.tags = tags;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return completed == task.completed &&
               Objects.equals(name, task.name) &&
                Objects.equals(creationTimestamp, task.creationTimestamp) &&
                Objects.equals(lastUpdateTimestamp, task.lastUpdateTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, completed, creationTimestamp, lastUpdateTimestamp);
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}
	
    @Override
	public String toString() {
		return "Task [name=" + name + ", project=" + project + ", assignedTo=" + assignedTo + ", completed=" + completed
				+ "]";
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
