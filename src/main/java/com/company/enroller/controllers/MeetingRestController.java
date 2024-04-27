package com.company.enroller.controllers;

import java.util.Collection;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings(@RequestParam(value = "sortBy", defaultValue = "") String sortMode,
                                         @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder,
                                         @RequestParam(value = "key", defaultValue = "") String title) {
        Collection<Meeting> meetings = meetingService.getAll(title, sortMode, sortOrder);
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
//        if (meetingService.findByTitle(meeting.getTitle()) != null) {
//            return new ResponseEntity<String>(
//                    "Unable to create. A meeting with tittle " + meeting.getTitle() + " already exist.",
//                    HttpStatus.CONFLICT);
//        }
//        meetingService.add(meeting);
//        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<?> delete(@PathVariable("id") String title) {
//        Meeting meeting = meetingService.findByTitle(title);
//        if (meeting == null) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//        meetingService.delete(meeting);
//        return new ResponseEntity<Participant>(HttpStatus.OK);
//    }



}
