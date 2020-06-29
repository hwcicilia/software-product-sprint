// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<String> attendees = request.getAttendees();
    ArrayList<TimeRange> relatedEvents = new ArrayList<TimeRange>();
    Collection<TimeRange> ans = new ArrayList<TimeRange>();

    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
        return ans;
    }

    for (Event event: events){
        for (String attendee: attendees) {
            if (event.getAttendees().contains(attendee)) {
                relatedEvents.add(event.getWhen());
                break;
            }
        }
    }

    if (relatedEvents.isEmpty()) {
        ans.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, TimeRange.END_OF_DAY, true));
        return ans;
    }

    Collections.sort(relatedEvents, TimeRange.ORDER_BY_START);

    int pointerA = 0, pointerB = 0;

    if (TimeRange.START_OF_DAY + request.getDuration() <= relatedEvents.get(0).start()) {
        ans.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, relatedEvents.get(0).start(), false));
    }

    while (pointerB < relatedEvents.size()) {
        if (relatedEvents.get(pointerA).end() >= relatedEvents.get(pointerB).end()) {
            pointerB++;
            continue;
        } else if (relatedEvents.get(pointerA).end() + request.getDuration() <= relatedEvents.get(pointerB).start()) {
            ans.add(TimeRange.fromStartEnd(relatedEvents.get(pointerA).end(), relatedEvents.get(pointerB).start(), false));
        }
        pointerA = pointerB;
        pointerB++;
    }

    if (relatedEvents.get(relatedEvents.size() - 1).end() + request.getDuration() <= TimeRange.END_OF_DAY) {
        ans.add(TimeRange.fromStartEnd(relatedEvents.get(pointerA).end(), TimeRange.END_OF_DAY, true));
    }

    return ans;
  }
}
