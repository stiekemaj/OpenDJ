/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2006-2008 Sun Microsystems, Inc.
 * Portions Copyright 2011-2016 ForgeRock AS.
 */
package org.opends.server.types.operation;
import org.forgerock.i18n.LocalizableMessageBuilder;


import java.util.List;

import org.opends.server.types.AdditionalLogItem;
import org.forgerock.opendj.ldap.DN;
import org.forgerock.opendj.ldap.ResultCode;



/**
 * This class defines a set of methods that are available for use by
 * post-response plugins for all types of operations.  Note that this
 * interface is intended only to define an API for use by plugins and
 * is not intended to be implemented by any custom classes.
 */
@org.opends.server.types.PublicAPI(
     stability=org.opends.server.types.StabilityLevel.UNCOMMITTED,
     mayInstantiate=false,
     mayExtend=false,
     mayInvoke=true)
public interface PostResponseOperation
       extends PluginOperation
{
  /**
   * Retrieves the result code for this operation.
   *
   * @return  The result code associated for this operation, or
   *          <CODE>UNDEFINED</CODE> if the operation has not yet
   *          completed.
   */
  ResultCode getResultCode();



  /**
   * Retrieves the error message for this operation.  Its contents may
   * be altered by the caller.
   *
   * @return  The error message for this operation.
   */
  LocalizableMessageBuilder getErrorMessage();



  /**
   * Retrieves the matched DN for this operation.
   *
   * @return  The matched DN for this operation, or <CODE>null</CODE>
   *          if the operation has not yet completed or does not have
   *          a matched DN.
   */
  DN getMatchedDN();



  /**
   * Retrieves the set of referral URLs for this operation.  Its
   * contents must not be altered by the caller.
   *
   * @return  The set of referral URLs for this operation, or
   *          <CODE>null</CODE> if the operation is not yet complete
   *          or does not have a set of referral URLs.
   */
  List<String> getReferralURLs();



  /**
   * Retrieves the authorization DN for this operation.  In many
   * cases, it will be the same as the DN of the authenticated user
   * for the underlying connection, or the null DN if no
   * authentication has been performed on that connection.  However,
   * it may be some other value if special processing has been
   * requested (e.g., the operation included a proxied authorization
   * control).
   *
   * @return  The authorization DN for this operation.
   */
  DN getAuthorizationDN();



  /**
   * Retrieves the time that processing stopped for this operation.
   * This will actually hold a time immediately before the response
   * was sent to the client.
   *
   * @return  The time that processing stopped for this operation.
   */
  long getProcessingStopTime();



  /**
   * Retrieves the length of time in milliseconds that the server
   * spent processing this operation.
   *
   * @return  The length of time in milliseconds that the server spent
   *          processing this operation.
   */
  long getProcessingTime();



  /**
   * Returns an unmodifiable list containing the additional log items for this
   * operation, which should be written to the log but not included in the
   * response to the client.
   *
   * @return An unmodifiable list containing the additional log items for this
   *         operation.
   */
  List<AdditionalLogItem> getAdditionalLogItems();
}

