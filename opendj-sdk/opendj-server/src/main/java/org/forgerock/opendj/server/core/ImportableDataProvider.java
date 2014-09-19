/*
* CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at legal-notices/CDDLv1_0.txt
 * or http://forgerock.org/license/CDDLv1.0.html.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at legal-notices/CDDLv1_0.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *       Copyright 2008 Sun Microsystems, Inc.
 *       Portions Copyright 2013-2014 ForgeRock AS.
 */
package org.forgerock.opendj.server.core;

import org.forgerock.opendj.ldap.FutureResult;
import org.forgerock.opendj.ldap.ResultHandler;
import org.forgerock.opendj.ldif.EntryReader;

/**
 * A data provider which supports LDIF import functionality.
 * <p>
 * FIXME: the async APIs used below are a bad fit. We do not want to return an
 * {@link LdapException}. We really need a more generic promises API.
 * <p>
 * FIXME: it would be nice if we can use EntryReader, however we may need to
 * provide an optimized implementation for use in multi-threaded imports. E.g.
 * performing DN checking as early as possible before doing schema validation.
 * <p>
 * FIXME: import allows you to append, merge, replace entries. Do we need to
 * expose that here?
 */
public interface ImportableDataProvider {

    /**
     * Returns the ID of this data provider.
     *
     * @return The ID of this data provider.
     */
    DataProviderID getDataProviderID();

    /**
     * Imports the contents of this data provider from the provided entry
     * reader.
     * <p>
     * Note that the server will not explicitly initialize this data provider
     * before calling this method.
     *
     * @param reader
     *            The entry reader.
     * @param handler
     *            A handler which will be notified when the import completes.
     * @return A future representing the completion of the import.
     */
    FutureResult<Void> importEntries(EntryReader reader, ResultHandler<Void> handler);
}
