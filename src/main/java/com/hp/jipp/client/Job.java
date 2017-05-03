package com.hp.jipp.client;

import com.google.auto.value.AutoValue;
import com.google.common.base.Optional;
import com.hp.jipp.encoding.AttributeGroup;

import java.io.IOException;

@AutoValue
public abstract class Job {

    static Job of(int id, Printer printer, AttributeGroup jobAttributes) {
        return new AutoValue_Job(id, printer, Optional.<JobRequest>absent(), jobAttributes);
    }

    static Job of(int id, JobRequest jobRequest, AttributeGroup jobAttributes) {
        return new AutoValue_Job(id, jobRequest.getPrinter(), Optional.of(jobRequest), jobAttributes);
    }

    public abstract int getId();

    public abstract Printer getPrinter();

    /** Original job request, if accessible */
    public abstract Optional<JobRequest> getJobRequest();

    /** Printer-supplied attributes for the job */
    public abstract AttributeGroup getAttributes();

    /** Returns a new Job containing more current JobAttributes from the enclosed response packet */
    public Job withAttributes(AttributeGroup newAttributes) throws IOException {
        return new AutoValue_Job(getId(), getPrinter(), getJobRequest(), newAttributes);
    }
}
