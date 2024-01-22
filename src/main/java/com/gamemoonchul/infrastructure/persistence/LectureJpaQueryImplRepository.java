package com.gamemoonchul.infrastructure.persistence;

import com.gamemoonchul.domain.Lecture;
import com.gamemoonchul.domain.QLecture;
import com.gamemoonchul.domain.dto.LectureRankingView;
import com.gamemoonchul.domain.dto.LectureSearchCondition;
import com.gamemoonchul.domain.dto.LectureView;
import com.gamemoonchul.domain.dto.TicketView;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.gamemoonchul.domain.QLecture.lecture;
import static com.gamemoonchul.domain.QTicket.ticket;

@Repository
public class LectureJpaQueryImplRepository extends QuerydslRepositorySupport implements LectureJpaQueryRepository {

    public LectureJpaQueryImplRepository() {
        super(Lecture.class);
    }

    private <T> JPQLQuery<T> applySearchCondition(JPQLQuery<T> query, LectureSearchCondition searchCondition) {
        if (searchCondition.hasEmployeeId()) {
            query.innerJoin(ticket).on(ticket.lecture.id.eq(lecture.id));
            query.where(ticket.employeeId.eq(searchCondition.employeeId()));
        }
        return query;
    }

    @Override
    public Page<LectureView> search(LectureSearchCondition condition, Pageable pageable) {
        QLecture lecture = QLecture.lecture;

        var jpqlQuery = getQuerydsl().createQuery().select(
                Projections.constructor(
                        LectureView.class,
                        lecture.id,
                        lecture.speaker,
                        lecture.venue,
                        lecture.content,
                        lecture.capacityOfAttendee,
                        lecture.attendeeCount,
                        lecture.startAt
                ))
                .from(lecture)
                .where(lecture.startAt.between(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(7)));

        var queryResults = applySearchCondition(jpqlQuery, condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public List<String> getAttendees(Long lectureId) {

        var ticketViews = getQuerydsl().createQuery().select(
                        Projections.constructor(
                                TicketView.class,
                                ticket.id,
                                ticket.lecture.id,
                                ticket.employeeId
                        ))
                .from(ticket)
                .where(ticket.lecture.id.eq(lectureId))
                .fetchResults()
                .getResults();

        var employeeIds = ticketViews.stream().map(
                ticketView -> ticketView.employeeId()
        ).toList();

        return employeeIds;
    }

    @Override
    public List<LectureRankingView> getPopularLectures() {
        var rankingViews = getQuerydsl().createQuery().select(
                        Projections.constructor(
                                LectureRankingView.class,
                                lecture.id,
                                lecture.speaker,
                                lecture.venue,
                                lecture.content,
                                lecture.capacityOfAttendee,
                                lecture.attendeeCount,
                                lecture.startAt,
                                lecture.id.count().intValue()
                        )).from(lecture)
                .leftJoin(ticket).on(lecture.id.eq(ticket.lecture.id))
                .groupBy(ticket.lecture.id)
                .orderBy(ticket.lecture.id.count().desc())
                .where(ticket.createdAt.after(LocalDateTime.now().minusDays(3)))
                .fetch();

        return rankingViews;
    }
}
