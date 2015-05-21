/**
 * Copyright (c) 2012-2015, jcabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.dynamo.retry;

import com.amazonaws.AmazonClientException;
import com.jcabi.aspects.Tv;
import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Table;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test case for {@link ReTable}.
 * @author Jason Wong (super132j@yahoo.com)
 * @version $Id$
 */
public final class ReTableTest {

    /**
     * ReTable can retry AWS Calls.
     * @throws Exception If some problem inside
     */
    @Test
    public void retriesAwsCalls() throws Exception {
        final Table table = Mockito.mock(Table.class);
        final Attributes attrs = new Attributes();
        final String msg = "Exception!";
        Mockito.doThrow(new AmazonClientException(msg)).when(table)
            .delete(attrs);
        final Table retry = new ReTable(table);
        try {
            retry.delete(attrs);
            Assert.fail("exception expected here");
        } catch (final AmazonClientException ex) {
            assert ex.getMessage().equals(msg);
        }
        Mockito.verify(table, Mockito.times(Tv.THREE)).delete(attrs);
    }
}
