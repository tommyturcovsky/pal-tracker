package io.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping(path="/time-entries", consumes="application/json", produces="application/json")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        return ResponseEntity.created(null).body(timeEntry);
    }

    @GetMapping(path="/time-entries/{timeEntryId}", produces="application/json")
    public ResponseEntity<TimeEntry> read(@PathVariable("timeEntryId") long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if (timeEntry == null) {
            return ResponseEntity.notFound().location(null).build();
        } else {
            return ResponseEntity.ok(timeEntry);
        }
    }

    @GetMapping(path="/time-entries", produces="application/json")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return ResponseEntity.ok(timeEntryList);
    }

    @PutMapping(path="/time-entries/{timeEntryId}", consumes="application/json", produces="application/json")
    public ResponseEntity<TimeEntry> update(@PathVariable("timeEntryId") long timeEntryId, @RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId, timeEntryToUpdate);
        if (timeEntry == null) {
            return ResponseEntity.notFound().location(null).build();
        } else {
            return ResponseEntity.ok(timeEntry);
        }
    }

    @DeleteMapping(path="/time-entries/{timeEntryId}", produces="application/json")
    public ResponseEntity<Void> delete(@PathVariable("timeEntryId") long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return ResponseEntity.noContent().build();
    }
}
