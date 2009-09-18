/*
Copyright (c) 2009 David Savage.

This file is part of "cong".

cong is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

cong is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with cong.  If not, see <http://www.gnu.org/licenses/>.
 */
package info.savaged.cong

/**
 * Simulation of the paper "Congregation Analysis Report (S-10)"
 *
 * The report should be submitted on the jw.org website by September 20th.
 * The data in this record should be based on that in the MeetingAttendance and
 * ServiceReport records for the past service year.
 *
 * @author savaged
 */
class YearAnalysis {

    // Year in which the service year starts (September to August).
    Integer serviceYearStart


    // Average weekly meeting attendance for Public Meeting (pt)
    Integer ptAverage
    // Average weekly meeting attendance for Watchtower Study (wt)
    Integer wtAverage
    // Average weekly meeting attendance for Congregation Bible Study (cbs)
    Integer cbsAverage
    // Average weekly meeting attendance for Service Meeting (sm)
    Integer smAverage


    // Active publishers: All publishers (including pioneers) who reported
    // service during one or more of the second half of the serivce year (March
    // to August). This should be greater than or equal to active baptised and
    // regular publishers.
    Integer activePublishers

    // Active baptized publishers: All baptised publishers (including pioneers)
    // who reported service during one or more of the second half of the serivce
    // year (March to August). In most cases, this number will be less than the
    // total number of active publishers and usually more than the total number
    // of regular publishers.
    Integer activeBaptisedPublishers

    // Regular publishers: All baptised publishers (including pioneers) who have
    // not missed a month in service for the second half of the serivce year
    // (March to August) (or since st arting, being reactivated, or being
    // reinstated). In most cases, this number will be less than the total
    // number of active publishers and usually less than the total number of
    // active baptized publishers.
    Integer regularPublishers

    // Auxiliary pioneers: All publishers who auxiliary pioneered at least one
    // month during the service year. No publisher should be counted twice.
    Integer auxiliaryPioneers

    // New publishers: All publishers who began publishing for the first time
    // during the service year.
    Integer newPublishers

    // Inactive publishers: All publishers who have not reported service for six
    // consecutive months. That six-month period could end in any of the months
    // during the service year. Those who became inactive in previous years and
    // have remained inactive should not be counted.
    Integer inactivePublishers

    // Reactivated publishers: All publishers who had been inactive but who
    // started reporting field service again in any of the months in the service
    // year. (It is possible for the same person to be included in the total
    // number of inactive publishers and the total number of reactivated
    // publishers.)
    Integer reactivatedPublishers

    // Elders: This number should only include those whose appointment has
    // already been confirmed by the branch office.
    Integer elders

    // Ministerial servants: This number should only include those whose appointment has
    // already been confirmed by the branch office.
    Integer servants

    static constraints = {
        serviceYearStart unique:true, range:2008..2018

        ptAverage min:1
        wtAverage min:1
        cbsAverage min:1
        smAverage min:1

        activePublishers min:1
        activeBaptisedPublishers min:1
        regularPublishers min:1
        auxiliaryPioneers nullable:false
        newPublishers nullable:false
        inactivePublishers nullable:false
        reactivatedPublishers nullable:false
        elders min:1
        servants nullable:false
    }
}
